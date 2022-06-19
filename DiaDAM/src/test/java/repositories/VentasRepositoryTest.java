package repositories;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.ManagerBBDD;
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
    private final ManagerBBDD db = ManagerBBDD.getInstance();
    LineaVenta ventita= new LineaVenta("hamburguesa",5.00, 1 , 5.00);
    List<LineaVenta> venta = new ArrayList<>();
    PersonasRepository personasRepository = PersonasRepository.getInstance(db);



    @Test
    void save() throws SQLException {
        venta.add(ventita);
        Persona personaTest = new Persona("mario", "gonzalez", "madrid, calle: baja, numero:10 ", "616494531", "9198765987658908", "mario@gmail.com", "19234678", null, "CLIENTE");
        var res= ventasRepository.save(venta,personaTest);
        assertAll(
                () -> assertEquals(ventita.getTotal(), res.getTotal())
        );
    }

}
