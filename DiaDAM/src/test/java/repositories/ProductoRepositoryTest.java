package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.utils.Resources;
import org.junit.jupiter.api.*;
import utilities.DataBase;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoRepositoryTest {

    // Inyección realizada con javafx
    @Inject
    ProductoRepository productoRepository;


    //Vamos a tener un producto
    Producto productoTest = new Producto(UUID.randomUUID().toString(), "Pollo", 10, 2.50, "Carne", Resources.getPath(DiaApplication.class, "images/carne.png"));


    @BeforeAll
    static void setUpAll(){
        //Inicializamos la base de datos, estructura de tablas
        DataBase.init();
    }

    @BeforeEach
    void setUp() throws SQLException, IOException {
        // En cada test borrramos los datos de la tabla productos
        DataBase.deleteAll();
    }

    @Test
    void findByDescripcion() throws SQLException{
        Optional<Producto> productoOp =productoRepository.findByDescripcion(productoTest.getDescripcion());
        assertAll(
                ()->assertTrue(productoOp.isPresent()),
                ()->assertEquals(productoOp.get().getId(), productoTest.getId()),
                () -> assertEquals(productoOp.get().getNombre(), productoTest.getNombre()),
                () -> assertTrue(productoOp.get().toString().equals(productoTest.toString()))
        );
    }

    @Test
    void findAll() throws SQLException{
        var res = productoRepository.findAll();

        assertAll(
                ()-> assertEquals(res.size(), 1),
                () -> assertTrue(res.contains(productoTest)),
                () -> assertEquals(res.get(0).getId(), productoTest.getId()),
                () -> assertEquals(res.get(0).getNombre(), productoTest.getNombre())
        );
    }

    @Test
    void save() throws SQLException, IOException {
        Producto productoTest = new Producto(UUID.randomUUID().toString(), "Merluza", 22, 3.50, "Pescado", Resources.getPath(DiaApplication.class, "images/carne.png"));
        var res = productoRepository.create(productoTest).get();
        Optional<Producto> productoOp = productoRepository.findByDescripcion(res.getDescripcion());

        assertAll(
                () -> assertEquals(res.getDescripcion(), productoTest.getDescripcion()),
                () -> assertTrue(productoOp.isPresent())
        );
    }

    void update() throws SQLException, IOException {
        productoTest.setNombre("Ternera");
        productoTest.setStock(15);
        productoTest.setPrecio(2.22);
        productoTest.setDescripcion("Carne");

        var res = productoRepository.update(productoTest).get();
        Optional<Producto> productoOp = productoRepository.findByDescripcion(productoTest.getDescripcion());

        assertAll(
                () -> assertEquals(res.getId(), productoTest.getId()),
                () -> assertEquals(res.getNombre(), productoTest.getNombre()),
                () -> assertEquals(res.getDescripcion(), productoTest.getDescripcion()),
                () -> assertTrue(productoOp.isPresent()),
                () -> assertEquals(productoOp.get().getId(), productoTest.getId()),
                () -> assertEquals(productoOp.get().getNombre(), productoTest.getNombre()),
                () -> assertEquals(productoOp.get().toString(), productoTest.toString())
        );
    }

    @Test
    void delete() throws SQLException{
        var res = productoRepository.delete(productoTest.getId()).get();
        Optional<Producto> paisOp = productoRepository.findByDescripcion(productoTest.getDescripcion());
        assertAll(
                () -> assertEquals(res.getId(), productoTest.getId()),
                () -> assertEquals(res.getNombre(), productoTest.getNombre()),
                () -> assertEquals(res.getDescripcion(), productoTest.getDescripcion()),
                () -> assertFalse(paisOp.isPresent())
        );
    }

}
