package es.diadam.diadam.utils;
import es.diadam.diadam.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    public void isEmailtest() {
        String email = "alvaro@dam.com";
        String emailIncorrecto = "alvaro@dam";
        String emailIncorrecto2 = "alvarodam.com";
        assertAll(
                () -> assertTrue(Utils.isEmail(email)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto2))
        );
    }

    @Test
    public void isTarjetaCreditoTest(){
        String tarjeta = "4657818439124503";
        String tarjetaIncorrecto = "398709897654788";
        String tarjetaIncorrecto2 = "13245674121232341";
        String tarjetaIncorrecto3 = "G234167412123234";
        assertAll(
                () -> assertTrue(Utils.isTarjeta(tarjeta)),
                () -> assertFalse(Utils.isTarjeta(tarjetaIncorrecto)),
                () -> assertFalse(Utils.isTarjeta(tarjetaIncorrecto2)),
                () -> assertFalse(Utils.isTarjeta(tarjetaIncorrecto3))
        );
    }
    public void isTelefonoTest(){
        String telefono = "636123143";
        String telefonoIncorrecto = "6361231431";
        String telefonoIncorrecto2 = "63612314";
        String telefonoIncorrecto3 = "6G6123143";
        String telefonoIncorrecto4 = "GFRGYHFRD";
        String telefonoIncorrecto5 = "GFRGYHFR7";
        String telefonoIncorrecto6 = "GFRGYHF87";
        String telefonoIncorrecto7 = "GFRGYH457";
        String telefonoIncorrecto8 = "GFRGY4657";
        String telefonoIncorrecto9 = "FFRG34567";
        String telefonoIncorrecto10 = "GFRGYHFR7";

        assertAll(
                () -> assertTrue(Utils.isTarjeta(telefono)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto2)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto3)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto4)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto5)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto6)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto7)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto8)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto9)),
                () -> assertFalse(Utils.isTarjeta(telefonoIncorrecto10))

        );
    }

}
