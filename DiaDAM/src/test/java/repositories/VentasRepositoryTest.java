package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.LineaVenta;
import es.diadam.diadam.models.Persona;

import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.repositories.VentasRepository;
import es.diadam.diadam.utils.Resources;

import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class VentasRepositoryTest {

    private final VentasRepository ventasRepository = VentasRepository.getInstance();
    LineaVenta ventita= new LineaVenta("hamburguesa",5.00, 2 , 10.00);
    List<LineaVenta> venta = new ArrayList<>();
    PersonasRepository personasRepository = PersonasRepository.getInstance();



    @Test
    void save() throws SQLException {
        Persona personaTest = new Persona("mario", "gonzalez", "madrid, calle: baja, numero:10 ", "616494531", "9198765987658908", "mario@gmail.com", "19234678", null, "CLIENTE");
        var res= ventasRepository.save(venta,personaTest);
        assertAll(
                () -> assertEquals(ventita.getTotal(), res.getTotal())
        );
    }

}
