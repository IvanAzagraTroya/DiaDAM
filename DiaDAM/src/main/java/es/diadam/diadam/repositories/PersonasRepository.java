package es.diadam.diadam.repositories;

import es.diadam.diadam.dto.PersonaDTO;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.services.StoragePersona;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Alvaro Mingo
 *
 */

public class PersonasRepository {
    private static PersonasRepository instance;
    private final ObservableList<Persona> repository = FXCollections.observableArrayList();
    private final StoragePersona storage = StoragePersona.getInstance();
    Logger logger = LogManager.getLogger(PersonasRepository.class);
    ManagerBBDD db = ManagerBBDD.getInstance();


    // Datos de ejemplo....
    /*public void initData(){
        if (repository.isEmpty()) {
            logger.info("Inicializando datos de ejemplo");
            try {
                    repository.add(new Persona(UUID.randomUUID().toString(),"Alvato","Mingo","Colmenar de oreja, calle: lopez","654123145","45432435" ,"alvarosmingos@gmail.com" ,"1234567" , Resources.getPath(DiaApplication.class,"images/PersonaDefectoClaro.png"),"ADMIN"));
                    repository.add(new Persona(UUID.randomUUID().toString(),"Jorge","Sanchez","Leganes, calle: Tijuana","644243244","98789876" ,"jorgesanchezs@gmail.com" ,"3343343" ,Resources.getPath(DiaApplication.class,"images/PersonaDefectoClaro.png"),"CLIENTE"));
                    repository.add(new Persona(UUID.randomUUID().toString(),"Ivan","Castillo","Colmenar de oreja, calle: benito","636432123","613241234" ,"ivanforever@gmail.com" ,"5467328" ,Resources.getPath(DiaApplication.class,"images/PersonaDefectoClaro.png"),"CLIENTE"));

            }catch(SQLException | IOException Exception e ){
                // TODO mirar qué error no permite usar SQLException
                logger.error("Error al inicializar datos");
            }

        }
    }*/



    public static PersonasRepository getInstance() {
        if (instance == null) {
            instance = new PersonasRepository();
        }
        return instance;
    }

    // Devuelve la lista de personas como una lista observable.
    public ObservableList<Persona> findAll() throws SQLException {
        String sql = "SELECT * FROM personas";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos las personas"));
        repository.clear();
        while (rs.next()) {
            // Lo llevamos a memoria
            repository.add(
                    new Persona(
                            rs.getString("id"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("tarjeta"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getString("avatar"),
                            rs.getString("tipo")
                    )
            );
        }
        db.close();
        if (repository.isEmpty()) {
            //initData();
        }
        return repository;
    }

    // Backup de la lista de personas.
    public void backup() throws IOException {
        List<PersonaDTO> personas = repository.stream().map(PersonaDTO::new).collect(Collectors.toList());
        storage.backup(personas);
    }


    public Persona findById(String id) throws SQLException {
        return repository.stream().filter(persona -> persona.getId().equals(id)).findFirst().orElseThrow(() -> new SQLException("No existe usuario con ese ID"));
    }


    public void storeAvatar(Persona p) throws IOException {
        String destination = Properties.IMAGES_DIR + File.separator + p.getId() + "." + Utils.getFileExtension(p.getFoto()).orElse("png");
        String source = p.getFoto().replace("file:", "");
        logger.info("Origen: " + source);
        logger.info("Destino: " + destination);
        storage.copyFile(source, destination);
        p.setFoto(destination);
    }

    public void deleteAvatar(Persona p) throws IOException {
        String source = Properties.IMAGES_DIR + File.separator + p.getId() + "." + Utils.getFileExtension(p.getFoto()).orElse("png");
        storage.deleteFile(source);
    }

    // Restaura la lista de personas.
    public void restore() throws IOException, ClassNotFoundException, SQLException {
        // Recogemos la lista de personas del backup.
        List<PersonaDTO> personas = storage.restore();
        repository.clear();
        // eliminamos la lista de personas de la base de datos.
        String sql = "DELETE FROM personas";
        db.open();
        db.update(sql);
        db.close();
        // Y la volvemos a añadir a la lista observable y la base de datos
        personas.forEach(p -> {
            try {
                create(p.fromDTO());
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Optional<Persona> delete(Persona persona) throws IOException, SQLException {
        // Eliminamos aquí su avatar.
        deleteAvatar(persona);
        // Eliminamos de bd.
        String sql = "DELETE FROM personas WHERE id = ?";
        db.open();
        db.delete(sql, persona.getId());
        db.close();
        // Eliminamos de memoria
        repository.remove(persona);
        return Optional.of(persona);
    }

    public void create(Persona persona) throws IOException, SQLException {
        // Salvamos su avatar.
        storeAvatar(persona);
        // Salvamos en la BBDD.
        String sql = "INSERT INTO personas (id, nombre, apellidos, direccion, telefono, tarjeta, email, contraseña, avatar, tipo) VALUES (? , ?, ?, ?, ?, ?, ?, ?,?, ?)";
        db.open();
        db.insert(sql, persona.getId(), persona.getNombre(), persona.getApellido(), persona.getDireccion(), persona.getTelefono(), persona.getTarjeta(), persona.getEmail(), persona.getContrasenia(), persona.getFoto(), persona.getTipo());
        db.close();
        // Salvamos en memoria
        repository.add(persona);

    }

    public Optional<Persona> update(Persona persona) throws IOException, SQLException {
        int index = repository.indexOf(persona);
        // Actualizamos su avatar.
        storeAvatar(persona);
        // Actualizamos en la base de datos
        String sql = "UPDATE personas SET nombre = ?, apellidos = ?, direccion = ?, telefono = ?, tarjeta = ?, email = ?,contraseña = ?, avatar = ? WHERE id = ?";
        db.open();
        db.update(sql, persona.getNombre(), persona.getApellido(), persona.getDireccion(), persona.getTelefono(), persona.getTarjeta(), persona.getEmail(),persona.getContrasenia(), persona.getFoto(), persona.getId());
        db.close();
        // Salvamos en memoria
        repository.set(index, persona);
        return Optional.of(persona);
    }
    public void deleteAll() throws IOException, SQLException {

        // Eliminamos de la base de datos

        String sql = "DELETE FROM personas";
        db.open();
        db.delete(sql);
        db.close();

    }
}
