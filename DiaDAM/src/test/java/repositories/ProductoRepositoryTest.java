package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jorge Sanchez Berrocoso
 */

class ProductoRepositoryTest {
    private static final ManagerBBDD db = ManagerBBDD.getInstance();
    private static final Storage storage = Storage.getInstance();
    private static final ProductoRepository productoRepository = ProductoRepository.getInstance(db,storage);
    private final Producto pTest1 = new Producto(UUID.randomUUID().toString(), "Pollo", 10, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png"));

    @BeforeAll
    static void setUp() throws SQLException {
        productoRepository.deleteAll();
    }

    @Test
    void findAll() throws SQLException {
        try {
            var resVacio = productoRepository.findAll();
            productoRepository.create(pTest1);
            var resLlenoOptional = productoRepository.findAll();
            assertAll(
                    () -> assertEquals(1, resLlenoOptional.size()),
                    () -> assertEquals(pTest1, resLlenoOptional.get(0))
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void findById() throws SQLException {
        try {
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
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getAvatar(), res.getAvatar())

            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
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
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getAvatar(), res.getAvatar())
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
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getAvatar(), res.getAvatar())

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
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getAvatar(), res.getAvatar())
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
                    () -> assertTrue(productoRepository.findById(pTest1.getId()).isEmpty())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}