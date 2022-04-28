package es.diadam.diadam.repositories;

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

    Optional<T> findById(ID id) throws SQLException;

    T save(T entity) throws SQLException;

    T update(ID id, T entity) throws SQLException;

    T delete(ID id) throws SQLException;
}
