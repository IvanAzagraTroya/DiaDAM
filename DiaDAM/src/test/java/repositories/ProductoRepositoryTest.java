package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.utils.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.DataBase;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductoRepositoryTest {
    private static final ProductoRepository productoRepository = ProductoRepository.getInstance();
    private final Producto pTest1 = new Producto(UUID.randomUUID().toString(), "Pollo", 10, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png"));
    private final Producto pTest2 = new Producto(UUID.randomUUID().toString(), "Solomillo", 5, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png"));
    private final Producto pTest3 = new Producto(UUID.randomUUID().toString(), "Pescadilla", 19, 1.50, "Pescado", Resources.getPath(DiaApplication.class, "images/pescado.png"));


    @BeforeAll
    static void setUp() throws SQLException {
        productoRepository.deleteAll();
    }
    @Test
    void findAll() throws SQLException {
        try {
            var resVacio = productoRepository.findAll();
            productoRepository.create(pTest1);
            productoRepository.create(pTest2);
            productoRepository.create(pTest3);
            var resLleno = productoRepository.findAll();
            assertAll(
                    () -> assertEquals(3, resLleno.size()),
                    () -> assertEquals(pTest1, resLleno.get(0)),
                    () -> assertEquals(pTest2, resLleno.get(1)),
                    () -> assertEquals(pTest3, resLleno.get(2))
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
}