/*package es.diadam.diadam.controllers;

import es.diadam.diadam.controller.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonaEditarViewControllerTest {

    @Test
    public void IsNombretest() {
        String nombre = "Marcos";
        String nombreIncorrecto1 = "1";
        String nombreIncorrecto2 = "******";
        String nombreIncorrecto3 = "";

        assertAll(
                () -> assertTrue(Controller.isNombre(nombre)),
                () -> assertFalse(Controller.isNombre(nombreIncorrecto1)),
                () -> assertFalse(Controller.isNombre(nombreIncorrecto2)),
                () -> assertFalse(Controller.isNombre(nombreIncorrecto3))

        )
    }

    @Test
    public void IsApellidostest() {
        String apellidos = "Funes Arribas";
        String apellidosIncorrectos1 = "2";
        String apellidosIncorrectos2 = ",,,,,,,";
        String apellidosIncorrectos3 = "";

        assertAll(
                () -> assertTrue(Controller.isApellidos(apellidos)),
                () -> assertFalse(Controller.isApellidos(apellidosIncorrectos1)),
                () -> assertFalse(Controller.isApellidos(apellidosIncorrectos2)),
                () -> assertFalse(Controller.isApellidos(apellidosIncorrectos3))

        )
    }

    @Test
    public void IsDirecciontest() {
        String direccion = "paseo de la ermita nº 15";
        String direccionIncorrecta1 = "3";
        String direccionIncorrecta2 = "paseo";
        String direccionIncorrecta3 = "******";
        String direccionIncorrecta4 = "";

        assertAll(
                () -> assertTrue(Controller.IsDireccion(direccion)),
                () -> assertFalse(Controller.IsDireccion(direccionIncorrecta1)),
                () -> assertFalse(Controller.IsDireccion(direccionIncorrecta2)),
                () -> assertFalse(Controller.IsDireccion(direccionIncorrecta3)),
                () -> assertFalse(Controller.IsDireccion(direccionIncorrecta4))

        )
    }

    @Test
    public void IsTelefonotest() {
        String telefono = "666555444";
        String telefonoIncorrecto1 = "a";
        String telefonoIncorrecto2 = "^^^^^";
        String telefonoIncorrecto3 = "";

        assertAll(
                () -> assertTrue(Controller.IsTelefono(telefono)),
                () -> assertFalse(Controller.IsTelefono(telefonoIncorrecto1)),
                () -> assertFalse(Controller.IsTelefono(telefonoIncorrecto2)),
                () -> assertFalse(Controller.IsTelefono(telefonoIncorrecto3))

        )
    }

    @Test
    public void isTarjetaTest(){
        String tarjeta = "1234567890123456";
        String tarjetaIncorrecta1 = "987654321098765";
        String tarjetaIncorrecta2 = "74108529637410852";
        String tarjetaIncorrecta3 = "012345678901234J";

        assertAll(
                () -> assertTrue(Controller.isTarjeta(tarjeta)),
                () -> assertFalse(Controller.isTarjeta(tarjetaIncorrecta1)),
                () -> assertFalse(Controller.isTarjeta(tarjetaIncorrecta2)),
                () -> assertFalse(Controller.isTarjeta(tarjetaIncorrecta3))
        );
    }


    @Test
    public void IsEmailtest() {
        String email = "Marcosfunes@madrid.com";
        String emailIncorrecto1 = "@Marcos";
        String emailIncorrecto2 = "marcos.es";
        String emailIncorrecto3 = "";

        assertAll(
                () -> assertTrue(Controller.isEmail(email)),
                () -> assertFalse(Controller.isEmail(emailIncorrecto1)),
                () -> assertFalse(Controller.isEmail(emailIncorrecto2)),
                () -> assertFalse(Controller.isEmail(emailIncorrecto3))

        )
    }


    @Test
    public void IsContraseniatest() {
        String contrasenia = "DAM123";
        String contraseniaIncorrecta1 = "";
        String contraseniaIncorrecta2 = "******";


        assertAll(
                () -> assertTrue(Controller.isContrasenia(contrasenia)),
                () -> assertFalse(Controller.isContrasenia(contraseniaIncorrecta1)),
                () -> assertFalse(Controller.isContrasenia(contraseniaIncorrecta2))

        )
    }



}*/