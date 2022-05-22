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
}
