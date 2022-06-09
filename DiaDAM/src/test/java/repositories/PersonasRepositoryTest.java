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
            UUID.randomUUID().toString(), "raul", "Mingo", "madrid, calle: lopez, numero:20 ", "616497321", "9123212345678908", "antionio@gmail.com", "12945678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );
    private final Persona pTest1V2 = new Persona(
            UUID.randomUUID().toString(), "ruben", "torres", "madrid, calle: bernard, numero:30 ", "616497321", "9123212345123458", "rubenio@gmail.com", "65745678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );
    private final Persona pTest2 = new Persona(
            UUID.randomUUID().toString(), "maria", "gonzalez", "madrid, calle: baja, numero:10 ", "616497321", "9198765345678908", "maria@gmail.com", "19275678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE"
    );


    @BeforeAll
    void setUp() throws SQLException, IOException {
        personaRepository.deleteAll();
    }


    @Test
    void findAll() {
        try {
            var resVacioOptional = personaRepository.findAll();

            personaRepository.create(pTest1);
            personaRepository.create(pTest2);

            var resLlenoOptional = personaRepository.findAll();

            assertAll(
                    () -> assertEquals(0, resLlenoOptional.size()),
                    () -> assertEquals(3, resLlenoOptional.size()),
                    () -> assertEquals(pTest1, resLlenoOptional.get(0)),
                    () -> assertEquals(pTest2, resLlenoOptional.get(1))

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findById() {
        try {
            personaRepository.create(pTest1);
            Optional <Persona> resOptional = Optional.ofNullable(personaRepository.findById(pTest1.getId()));
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getId(), res.getId()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getApellido(), res.getApellido()),
                    () -> assertEquals(pTest1.getTelefono(), res.getTelefono()),
                    () -> assertEquals(pTest1.getApellido(), res.getTarjeta()),
                    () -> assertEquals(pTest1.getEmail(), res.getEmail()),
                    () -> assertEquals(pTest1.getContrasenia(), res.getContrasenia()),
                    () -> assertEquals(pTest1.getFoto(), res.getFoto()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())

            );
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    void save() throws SQLException, IOException {
        Persona personaTest = new Persona(UUID.randomUUID().toString(), "mario", "gonzalez", "madrid, calle: baja, numero:10 ", "616494531", "9198765987658908", "mario@gmail.com", "19234678", Resources.getPath(DiaApplication.class, "images/PersonaDefectoClaro.png"), "CLIENTE");
        personaRepository.create(pTest1);
        Optional<Persona> productoOp = Optional.ofNullable(personaRepository.findById(pTest1.getId()));

        assertAll(
                () -> assertEquals(pTest1.getId(), personaTest.getId()),
                () -> assertTrue(productoOp.isPresent())
        );
    }

    @Test
    void delete() throws SQLException, IOException {
        var res = personaRepository.delete(pTest2).get();
        Optional<Persona> personaOp = Optional.ofNullable(personaRepository.findById(pTest2.getId()));
        assertAll(
                () -> assertEquals(res.getId(), pTest2.getId()),
                () -> assertEquals(res.getNombre(), pTest2.getNombre()),
                () -> assertEquals(res.getApellido(), pTest2.getApellido()),
                () -> assertFalse( personaOp.isPresent())
        );
    }






    @Test
    void update() throws SQLException, IOException {
        pTest1V2.setNombre("pepe");
        pTest1V2.setApellido("ronaldo");
        pTest1V2.setTarjeta("1234567890987654");
        pTest1V2.setTelefono("");
        pTest1V2.setTarjeta("1234567890987654");
        pTest1V2.setEmail("pepe@gmail.com");
        pTest1V2.setContrasenia("12345434");
        pTest1V2.setEmail("CLIENTE");

        var res = personaRepository.update(pTest1V2).get();
        Optional<Persona> personaOp = Optional.ofNullable(personaRepository.findById(pTest1V2.getId()));

        assertAll(
                () -> assertEquals(res.getId(), pTest1V2.getId()),
                () -> assertEquals(res.getNombre(), pTest1V2.getNombre()),
                () -> assertEquals(res.getId(), pTest1V2.getId())
        );
    }

}

