package es.diadam.diadam.repositories;

import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Producto;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface IProductosRepository extends CRUDRepository<Producto, Integer> {

    Optional<Producto> create(Producto producto) throws SQLException, IOException;



    Optional<Producto> update(Producto producto) throws SQLException, IOException;

    Optional<Producto> delete(Producto producto) throws SQLException, IOException;

   void deleteAvatar(Producto producto) throws IOException;

    void storeAvatar(Producto producto) throws IOException;

    Optional<Producto> findByDescripcion(String descripcion) throws SQLException;


}
