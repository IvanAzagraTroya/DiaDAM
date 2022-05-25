package es.diadam.diadam.controllers;

import es.diadam.diadam.models.Persona;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra Troya
 */

public class IniciarSesionController {
    Logger logger = LogManager.getLogger(IniciarSesionController.class);

    @Inject
    PersonasRepository personasRepository;

    @FXML
    // Se introduce el email del usuario que se tendrá que mirar mediante regex
    private TextField txtEmail;

    @FXML
    // COntraseña del usuario
    private TextField txtContrasenia;

    private void accionRegistrarse() throws SQLException {
        // Se pasan los parámetros del usuario al método
        String email = txtEmail.getText();
        String contra = txtContrasenia.getText();

        // Depuración
        System.out.println("Email: ["+email+ "]");
        System.out.println("Contraseña: ["+contra+"]");

        // Si se introducen mal los campos se muestra un mensaje de error.
        Alert alert;
        if (email.isEmpty() || contra.isEmpty() || !Utils.isEmail(txtEmail.getText()) || compruebaEmail()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uno de los campos está vacío.");
            alert.setContentText("Asegurese de introducir email y contraseña");
            if(email.isEmpty())
                txtEmail.requestFocus();
            else txtContrasenia.requestFocus();
        }
        else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Datos introducidos");
            alert.setHeaderText("Los datos han sido introducidos correctamente: ");
            alert.setContentText("Email: "+email+ System.lineSeparator()+"Contraseña: " +contra);
        }
        alert.showAndWait();
    }

    private void accionSalir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seguro que quieres salir?");

        // Detecta qué se pulsa
        Optional<ButtonType> result = alert.showAndWait();
        // Aceptar
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            txtEmail.requestFocus();
        }
    }

    private void accionLimpiar() {
        txtEmail.setText("");
        txtContrasenia.setText("");
        txtEmail.requestFocus();
    }
    @FXML
    private void linkGoogle(ActionEvent event) {
        try {
            new ProcessBuilder("x-www-browser", "https://accounts.google.com/signin/v2/identifier?passive=1209600&continue=https%3A%2F%2Faccounts.google.com%2FEditPasswd%3Fhl%3Des&followup=https%3A%2F%2Faccounts.google.com%2FEditPasswd%3Fhl%3Des&hl=es&flowName=GlifWebSignIn&flowEntry=ServiceLogin").start();
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir la página");
            alert.setContentText(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean compruebaEmail() throws SQLException {
        boolean existe = false;
        for(Persona persona: personasRepository.findAll()){
            if(persona.getEmail() == txtEmail.getText()) {
                existe = true;
            }
            return existe;
        }
        return false;
    }
}
