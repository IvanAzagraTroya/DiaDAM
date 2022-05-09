package es.diadam.diadam.repositories;

import es.diadam.diadam.dto.ProductoDTO;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.services.Storage;
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
import java.util.stream.Collectors;

/**
 * @author Jorge Sánchez Berrocoso
 */
public class ProductosRepository {
    private static ProductosRepository instance;
    private final ObservableList<Producto> repository = FXCollections.observableArrayList();
    private final Storage storage = Storage.getInstance();
    Logger logger = LogManager.getLogger(ProductosRepository.class);
    ManagerBBDD db = ManagerBBDD.getInstance();

    private ProductosRepository() {
        // si no tenemos nada.. Esto es opcional, lo ideal es empezar vacío....
        //initData();
    }

    private void initData(){
        //Preguntar si quieren empezar con un initdata
    }

    public static ProductosRepository getInstance() {
        if (instance == null) {
            instance = new ProductosRepository();
        }
        return instance;
    }

    public ObservableList<Producto> getAll() throws SQLException {
        String sql = "SELECT * FROM productos";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los productos"));
        repository.clear();
        while (rs.next()) {
            // Lo llevamos a memoria
            repository.add(
                    new Producto(
                            rs.getString("id"),
                            rs.getString("nombre"),
                            rs.getInt("stock"),
                            rs.getDouble("cantidad"),
                            rs.getString("avatar"),
                            rs.getString("descripcion")
                    )
            );
        }
        db.close();
        if (repository.isEmpty()) {
            initData();
        }
        return repository;
    }

    public Producto findById(String id) throws SQLException {
        return repository.stream().filter(producto -> producto.getId().equals(id)).findFirst().orElseThrow(() -> new SQLException("No existe el producto con ese ID"));
    }

    // Backup de la lista de productos.
    public void backup() throws IOException {
        List<ProductoDTO> productos = repository.stream().map(ProductoDTO::new).collect(Collectors.toList());
        storage.backup(productos);
    }

    // Restaura la lista de productos.
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

        public void delete(Producto producto) throws IOException, SQLException {
            deleteAvatar(producto);
            String sql = "DELETE FROM productos WHERE id = ?";
            db.open();
            db.delete(sql, producto.getId());
            db.close();
            // Eliminamos de memoria
            repository.remove(producto);
        }

    public void create(Producto producto) throws IOException, SQLException {
        storeAvatar(producto);
        String sql = "INSERT INTO productos (id, nombre, stock, precio, avatar, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        db.open();
        db.insert(sql, producto.getId(), producto.getNombre(), producto.getStock(), producto.getPrecio(), producto.getAvatar(),producto.getDescripcion());
        db.close();
        // Salvamos en memoria
        repository.add(producto);

    }

    public void update(Producto producto) throws IOException, SQLException {
        int index = repository.indexOf(producto);
        storeAvatar(producto);
        String sql = "UPDATE personas SET nombre = ?, stock = ?, precio = ?, avatar = ?, descripcion = ?";
        db.open();
        db.update(sql, producto.getNombre(), producto.getStock(), producto.getPrecio(), producto.getAvatar(), producto.getDescripcion());
        db.close();
        repository.set(index, producto);

    }
    private void storeAvatar(Producto producto) throws IOException {
        String destination = Properties.IMAGES_DIR + File.separator + producto.getId() + "." + Utils.getFileExtension(producto.getAvatar()).orElse("png");
        String source = producto.getAvatar().replace("file:", "");
        logger.info("Origen: " + source);
        logger.info("Destino: " + destination);
        storage.copyFile(source, destination);
        producto.setAvatar(destination);
    }

        private void deleteAvatar(Producto producto) throws IOException {
            String source = Properties.IMAGES_DIR + File.separator + producto.getId() + "." + Utils.getFileExtension(producto.getAvatar()).orElse("png");
            storage.deleteFile(source);
    }
}

