package es.diadam.diadam.repositories;

import es.diadam.diadam.models.Persona;

import java.io.IOException;
import java.sql.SQLException;

public interface IPersonasRepository extends CRUDRepository<Persona, Integer>{
    void deleteAll() throws SQLException;
    void comprar() throws SQLException;

}
