package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.LineaVenta;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Venta;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.repositories.VentasRepository;
import es.diadam.diadam.utils.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VentasRepositoryTest {

    private final VentasRepository ventasRepository = VentasRepository.getInstance();
    LineaVenta ventita= new LineaVenta("hamburguesa",5.00, 2 , 10.00);
    List<LineaVenta> venta = new ArrayList<LineaVenta>();



    @Test
    void save(){
        Persona personaTest = new Persona(UUID.randomUUID().toString(), "mario", "gonzalez", "madrid, calle: baja, numero:10 ", "616494531", "9198765987658908", "mario@gmail.com", "19234678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE");
        ventasRepository.save(pTest);
        Optional<Persona> productoOp = Optional.ofNullable(personaRepository.findById(pTest.getId()));

        assertAll(
                () -> assertEquals(pTest1.getId(), personaTest.getId()),
                () -> assertTrue(productoOp.isPresent())
        );
    }
}
