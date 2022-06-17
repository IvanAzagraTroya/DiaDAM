package repositories;
import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.utils.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PersonasRepositoryTest {
    private final PersonasRepository personaRepository = PersonasRepository.getInstance();

    private final Persona pTest1 = new Persona(
            "raul", "Mingo", "madrid, calle: lopez, numero:20 ", "616497321", "9123212345678908", "antionio@gmail.com", "12945678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );
    private final Persona pTest2 = new Persona(
            "ruben", "torres", "madrid, calle: bernard, numero:30 ", "616497321", "9123212345123458", "rubenio@gmail.com", "65745678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );
    private final Persona pTest3 = new Persona(
            "maria", "gonzalez", "madrid, calle: baja, numero:10 ", "616497321", "9198765345678908", "maria@gmail.com", "19275678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );





    @Test
    void findAll() {
        try {
            personaRepository.deleteAll();
            var resVacioOptional = personaRepository.findAll();
            personaRepository.create(pTest1);
            var resLlenoOptional = personaRepository.findAll();

            assertAll(
                    () -> assertEquals(1, resLlenoOptional.size())

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findById() throws SQLException, IOException {

            personaRepository.create(pTest1);
            var resOptional = Optional.ofNullable(personaRepository.findById(pTest1.getId()));
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getApellido(), res.getApellido()),
                    () -> assertEquals(pTest1.getTelefono(), res.getTelefono()),
                    () -> assertEquals(pTest1.getTarjeta(), res.getTarjeta()),
                    () -> assertEquals(pTest1.getEmail(), res.getEmail()),
                    () -> assertEquals(pTest1.getContrasenia(), res.getContrasenia()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())

            );

    }
    @Test
    void save() throws SQLException, IOException {
        personaRepository.create(pTest1);
        var productoOp = Optional.ofNullable(personaRepository.findById(pTest1.getId()));

        assertAll(
                () -> assertEquals(pTest1.getNombre(), pTest1.getNombre()),
                () -> assertTrue(productoOp.isPresent())
        );
    }

    @Test
    void delete() throws SQLException, IOException {
        personaRepository.create(pTest1);
        var resOpt = personaRepository.delete(pTest1);
        var res = resOpt.get();

        assertAll(
                () -> assertEquals(res.getNombre(), pTest1.getNombre()),
                () -> assertEquals(res.getApellido(), pTest1.getApellido()),
                () -> assertTrue( resOpt.isPresent())
        );
    }






    @Test
    void update() throws SQLException, IOException {
        personaRepository.create(pTest2);
        pTest2.setNombre("pepe");
        pTest2.setApellido("ronaldo");


        var res = personaRepository.update(pTest2).get();
        var personaOp = Optional.ofNullable(personaRepository.findById(pTest2.getId()));

        assertAll(
                () -> assertTrue(personaOp.isPresent()),
                () -> assertEquals(pTest2.getNombre(), res.getNombre()),
                () -> assertEquals(pTest2.getApellido(), res.getApellido())


        );
    }

}

