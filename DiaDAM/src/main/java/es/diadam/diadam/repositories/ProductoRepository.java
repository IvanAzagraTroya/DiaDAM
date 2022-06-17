package es.diadam.diadam.repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.dto.ProductoDTO;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jorge Sanchez Berrocoso
 */


public class ProductoRepository implements IProductosRepository{
    private static ProductoRepository instance;
    private final ObservableList<Producto> repository = FXCollections.observableArrayList();
    Logger logger = LogManager.getLogger(ProductoRepository.class);
    Storage storage = Storage.getInstance();
    ManagerBBDD db =ManagerBBDD.getInstance();



  
    public static ProductoRepository getInstance() {
        if (instance == null) {
            instance = new ProductoRepository();
        }
        return instance;
    }



/*
    private void initData() {
        if(repository.isEmpty()){
            logger.info("Inicializando datos");
            try{
            repository.add(new Producto(UUID.randomUUID().toString(), "Pollo", 10, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Merluza", 15, 2.00, "Pescado", Resources.getPath(DiaApplication.class, "images/pescado.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Ternera", 12, 2.30, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Pescadilla", 19, 1.50, "Pescado", Resources.getPath(DiaApplication.class, "images/pescado.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Solomillo", 9, 2.90, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Salmon", 17, 2.20, "Pescado", Resources.getPath(DiaApplication.class, "images/pescado.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Pavo", 10, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png")));
            repository.add(new Producto(UUID.randomUUID().toString(), "Atun", 10, 3.00, "Pescado", Resources.getPath(DiaApplication.class, "images/pescado.png")));
            }catch(Exception e){
                logger.error("Error al inicializar datos");
            }
        }
    }
*/


    public void backup() throws IOException {
        List<ProductoDTO> producto = repository.stream().map(ProductoDTO::new).collect(Collectors.toList());
        storage.backup(producto);
    }

    @Override
    public ObservableList<Producto> findAll() throws SQLException {
        String sql = "SELECT * FROM productos";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos las productos"));
        ArrayList<Producto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(
                    new Producto(
                            rs.getString("id"),
                            rs.getString("nombre"),
                            rs.getInt("stock"),
                            rs.getDouble("cantidad"),
                            rs.getString("descripcion"),
                            rs.getString("avatar")
                           
                    )
            );
        }
    db.close();

        return FXCollections.observableList(list);
    }



    public void restore() throws IOException, ClassNotFoundException, SQLException {
        List<ProductoDTO> productos = storage.restore();
        repository.clear();
        String sql = "DELETE FROM productos";
        db.open();
        db.update(sql);
        db.close();
        productos.forEach(p -> {
            try {
                create(p.fromDTO());
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Override
    public Optional<Producto> findById(String id) throws SQLException {
        String query = "SELECT * FROM producto WHERE id = ?";
        db.open();
        ResultSet result = db.select(query, id).orElseThrow(() -> new SQLException("Error al consultar producto con id " + id));
        if (result.first()) {
            Producto producto = new Producto(
                    result.getString("id"),
                    result.getString("nombre"),
                    result.getInt("stock"),
                    result.getDouble("cantidad"),
                    result.getString("avatar"),
                    result.getString("descripcion")
            );
            db.close();
            return Optional.of(producto);
        }
        return Optional.empty();
    }



    @Override
    public Optional<Producto> create(Producto producto) throws SQLException, IOException {
      String id = UUID.randomUUID().toString();
       // storeAvatar(producto);

        String sql = "INSERT INTO productos (id, nombre, stock, cantidad, descripcion, avatar) VALUES (?, ?, ?, ?, ?, ?)";
        db.open();
        ResultSet res= db.insert(sql,id , producto.getNombre(), producto.getStock(), producto.getPrecio(), producto.getDescripcion(), producto.getAvatar())
                .orElseThrow(() -> new SQLException("Error al insertar pais"));
        if (res.first()) {
            producto.setId(res.getString(1));
        db.close();
        repository.add(producto);
        return Optional.of(producto);
    }
        return Optional.empty();
    }

    @Override
    public Optional<Producto> update(Producto producto) throws SQLException, IOException {
        int index = repository.indexOf(producto);
        //storeAvatar(producto);
        String sql = "UPDATE productos SET nombre = ?, stock = ?, cantidad = ?, descripcion = ?, avatar=? WHERE id = ?";
        db.open();
        int res = db.update(sql, producto.getId(), producto.getNombre(), producto.getStock(), producto.getPrecio(), producto.getDescripcion(), producto.getAvatar());
        db.close();
        repository.set(index, producto);
        
        return Optional.of(producto);
    }

    @Override
    public Optional<Producto> delete(Producto producto) throws SQLException, IOException {

        String sql = "DELETE FROM productos WHERE nombre = ?";
        db.open();
        db.delete(sql, producto.getNombre());
        db.close();
        repository.remove(producto);
        return Optional.of(producto);
    }

    @Override
    public void deleteAvatar(Producto producto) throws IOException {/*
        String source = Properties.IMAGES_DIR + File.separator + producto.getId() + "." + Utils.getFileExtension(producto.getAvatar()).orElse("png");
        storage.deleteFile(source);
        */
    }



    @Override
    public void storeAvatar(Producto producto) throws IOException {
        String destination = Properties.IMAGES_DIR + File.separator + producto.getId() + "." + Utils.getFileExtension(producto.getAvatar()).orElse("png");
        String source = producto.getAvatar().replace("file:", "");
        logger.info("Origen: " + source);
        logger.info("Destino: " + destination);
        storage.copyFile(source, destination);
        producto.setAvatar(destination);
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM productos";
        db.open();
        db.delete(sql);
        db.close();
    }

}
