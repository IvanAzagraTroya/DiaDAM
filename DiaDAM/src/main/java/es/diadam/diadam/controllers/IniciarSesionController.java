package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra Troya
 */

public class IniciarSesionController {
    Logger logger = LogManager.getLogger(IniciarSesionController.class);


    PersonasRepository personasRepository = PersonasRepository.getInstance();

    @FXML
    // Se introduce el email del usuario que se tendrá que mirar mediante regex
    private TextField txtEmail;

    @FXML
    // Contraseña del usuario
    private TextField txtContrasenia;
    
    // ----------------------------- parametros de registro --------------------------

    //---------------------------------------------------------------------------------

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void accionIniciar() throws SQLException, IOException {
        // Se pasan los parámetros del usuario al método
        String email = txtEmail.getText();
        String contra = txtContrasenia.getText();

        // Depuración
        logger.info("Email: ["+email+ "]");
        logger.info("Contraseña: ["+contra+"]");

        // Si se introducen mal los campos se muestra un mensaje de error.
        Alert alert;
        if (email.isEmpty() || contra.isEmpty() || !Utils.isEmail(txtEmail.getText()) || !compruebaContraseña()) {
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
            // Comprueba los métodos del email y la contraseña con la persona para ver si existe
            // si devuelven true se carga la escena del carrito, sino entra en el else y hace el focus
            // al campo equivocado, o al menos debería.
            if(compruebaEmail() && compruebaContraseña())
                // comprueba si el tipo del usuario es cliente o administrador, para así cargar la escena de edición de productos.
                if(compruebaTipo()) {
                    SceneManager.get().initInterfazAdministrador();
                } else {
                    // Si el tipo es de cliente se carga la escena del catalogo.
                    SceneManager.get().initInterfazCliente();
                }

            else{
                alert.setTitle("Ha ocurrido un error");
                alert.setContentText("Email o contraseña incorrectos");
                if(!compruebaEmail())
                    txtEmail.requestFocus();
                else if(!compruebaContraseña())
                    txtContrasenia.requestFocus();
            }
        }
        alert.showAndWait();
        dialogStage.close();
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
    
    private void accionRegistro() throws IOException {
        SceneManager.get().initRegistro();
    }
    @FXML
    private void linkGooglebtn(ActionEvent event) {
        try {
            new ProcessBuilder("x-www-browser", "https://accounts.google.com/signin/v2/identifier?passive=1209600&continue=https%3A%2F%2Faccounts.google.com%2FEditPasswd%3Fhl%3Des&followup=https%3A%2F%2Faccounts.google.com%2FEditPasswd%3Fhl%3Des&hl=es&flowName=GlifWebSignIn&flowEntry=ServiceLogin").start();
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir la página");
            alert.setContentText(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void accionLimpiarbtn(ActionEvent event) {
        logger.info("Limpiar datos");
        accionLimpiar();
    }
    
    @FXML
    private void accionIniciarbtn(ActionEvent event) throws SQLException, IOException{
        logger.info("Se ha iniciado sesión");
        accionIniciar();
    }
    
    @FXML 
    private void accionSalirbtn(ActionEvent event){
        logger.info("Se va a salir");
        accionSalir();
    }
    
    @FXML
    private void accionRegistrobtn(ActionEvent event) throws IOException{
        logger.info("Se abre la pantalla de registro");
        accionRegistro();
        
    }

    private boolean compruebaEmail() throws SQLException {
        boolean existe = false;
        for(Persona persona: personasRepository.findAll()){
            logger.info(persona);
            if(persona.getEmail() == txtEmail.getText()) {
                existe = true;
            }
            return existe;
        }
        return false;
    }
    
    private boolean compruebaContraseña() throws SQLException {
        boolean valid = false;
        for(Persona persona : personasRepository.findAll()) {
            logger.info(persona);
            if(persona.getContrasenia() == txtContrasenia.getText())
                valid = true;
        }
        return valid;
    }

    private boolean compruebaTipo() throws SQLException {
        boolean valid = false;
        for(Persona persona : personasRepository.findAll()) {
            if(persona.getTipo() == "Administrador")
                valid = true;
        }
        return valid;
    }
}
