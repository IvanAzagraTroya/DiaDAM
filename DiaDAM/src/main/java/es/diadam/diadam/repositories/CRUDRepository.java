package es.diadam.diadam.repositories;

import es.diadam.diadam.models.Producto;

import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra Troya
 * @param <T>
 * @param <ID>
 * CRUD simple
 */

public interface CRUDRepository<T, ID> {

    List<T> findAll() throws SQLException;








}
