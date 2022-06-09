package repositories;

import es.diadam.diadam.models.Carrito;
import es.diadam.diadam.repositories.CarritoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Jorge Sanchez Berrocoso
 */
@DisplayName("Carro Compra Test")
public class CarritoRepositoryTest {
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();

    private final Carrito pTest1 = new Carrito("Hamburguesa",5.0, 6, "");

    @BeforeAll
    void setUp(){
        carritoRepository.clear();
    }

    @Test
    void findAll(){
        try {
            var resVacio = carritoRepository.getItems();
            carritoRepository.addItem(pTest1);
            var resLleno = carritoRepository.getItems();

            assertAll(
                    () -> assertEquals(3, resLleno.size()),
                    () -> assertEquals(pTest1, resLleno.get(0))
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void addItem() {
        try {
            var res = carritoRepository.addItem(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getCantidadProductos(), res.getCantidadProductos())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void removeItem() {
        try {
            carritoRepository.addItem(pTest1);
            var res = carritoRepository.removeItem(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getCantidadProductos(), res.getCantidadProductos())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void getTotal() {
        try {
            carritoRepository.addItem(pTest1);
            var res = carritoRepository.getTotal();
            assertAll(
                    () -> assertEquals(5.0, res)
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void clear(){
        try {
            carritoRepository.addItem(pTest1);
            carritoRepository.clear();

            assertTrue(carritoRepository.getItems().isEmpty());

        }catch (Exception e){
            fail();
        }
    }

}
