package repositories;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.services.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jorge Sanchez Berrocoso
 */

class ProductoRepositoryTest {
    private final ManagerBBDD db = ManagerBBDD.getInstance();
    private final Storage storage = Storage.getInstance();
    private  final ProductoRepository productoRepository = ProductoRepository.getInstance(db,storage);
    private final Producto pTest1 = new Producto("Pollo", 10, 2.50, "Carne",null);



    @Test
    void findAll() throws SQLException {
        try {
            productoRepository.deleteAll();
            var resVacio = productoRepository.findAll();
            productoRepository.create(pTest1);
            var resLlenoOptional = productoRepository.findAll();
            assertAll(
                    () -> assertEquals(1, resLlenoOptional.size())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void findById() throws SQLException, IOException {

            productoRepository.create(pTest1);
            var resOptional = productoRepository.findById(pTest1.getId());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getId(), res.getId()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getStock(), res.getStock()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion())

            );
    }

    @Test
    void create() throws SQLException {
        try {
            var resOptional = productoRepository.create(pTest1);
            var res = resOptional.get();
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getId(), res.getId()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getStock(), res.getStock()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void update() throws SQLException {
        try {
            productoRepository.create(pTest1);
            pTest1.setNombre("Solomillo");
            pTest1.setPrecio(3.0);
            var resUpdate = productoRepository.update(pTest1);
            var resOpt = productoRepository.findById(pTest1.getId());
            var res = resOpt.get();
            assertAll(
                    () -> assertTrue(resOpt.isPresent()),
                    () -> assertEquals(pTest1.getId(), res.getId()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getStock(), res.getStock()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion())

            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void delete() throws SQLException {
        try {
            productoRepository.create(pTest1);
            var resOpt = productoRepository.delete(pTest1);
            var res = resOpt.get();


            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getId(), res.getId()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getStock(), res.getStock()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void deleteAll() throws SQLException {
        try {
            productoRepository.create(pTest1);
            productoRepository.deleteAll();

            assertAll(
                    () -> assertTrue(productoRepository.findAll().isEmpty()),
                    () -> assertFalse(productoRepository.findById(pTest1.getId()).isEmpty())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}