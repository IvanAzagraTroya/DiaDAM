package es.diadam.diadam.controllers;

import es.diadam.diadam.models.Persona;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ivan Azagra
 */
public class RegistroSesionController {
    Logger logger = LogManager.getLogger(RegistroSesionController.class);
    
    PersonasRepository personasRepository = PersonasRepository.getInstance();
    
    @FXML
    // Se introduce el email del usuario que se tendrá que mirar mediante regex
    private TextField txtEmailRegistro;

    @FXML
    // Contraseña del usuario
    private TextField txtContraseniaRegistro;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtApellidos;
    
    @FXML
    private TextField txtDireccion;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TextField txtTarjeta;
            
    
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
        private void accionRegistrarse() throws SQLException, IOException {
        // Se pasan los parámetros del usuario al método
        String emailRegistro = txtEmailRegistro.getText();
        String contraRegistro = txtContraseniaRegistro.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String tarjeta = txtTarjeta.getText();
        
        Persona newPersona = new Persona();
        
        // Depuración
        logger.info("Email: ["+emailRegistro+ "]");
        logger.info("Contraseña: ["+contraRegistro+"]");

        // Si se introducen mal los campos se muestra un mensaje de error.
        Alert alert;
        if (emailRegistro.isEmpty() || contraRegistro.isEmpty() || !Utils.isEmail(txtEmailRegistro.getText()) || compruebaEmail()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uno de los campos está vacío.");
            alert.setContentText("Asegurese de introducir email y contraseña");
            if(emailRegistro.isEmpty() || compruebaEmail()){
                txtEmailRegistro.requestFocus();
                alert.setTitle("Email incorrecto");
                alert.setContentText("El email o esta vacio o ya se encuentra registrado");
            }
            else if(contraRegistro.isEmpty()) txtContraseniaRegistro.requestFocus();
        }
        else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Datos introducidos");
            alert.setHeaderText("Los datos han sido introducidos correctamente: ");
            alert.setContentText("Email: "+emailRegistro+ System.lineSeparator()+"Contraseña: " +contraRegistro);
           
            newPersona.getId();
            newPersona.setNombre(nombre);
            newPersona.setApellido(apellidos);
            newPersona.setDireccion(direccion);
            newPersona.setTelefono(telefono);
            newPersona.setTarjeta(tarjeta);
            newPersona.setEmail(emailRegistro);
            newPersona.setContrasenia(contraRegistro);
            newPersona.setFoto("images/PersonaDefectoClaro.png");
            personasRepository.create(newPersona);
            
        }
        alert.showAndWait();
        dialogStage.close();
    }
        
        
    @FXML
    private void accionCrearUsuariobtn(ActionEvent event) throws SQLException, IOException {
        logger.info("Se ha creado un nuevo usuario");
        accionRegistrarse();
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
            txtEmailRegistro.requestFocus();
        }
    }

    private void accionLimpiar() {
        txtEmailRegistro.setText("");
        txtContraseniaRegistro.setText("");
        txtEmailRegistro.requestFocus();
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
    private void accionSalirbtn(ActionEvent event){
        logger.info("Se va a salir");
        accionSalir();
    }
    
    private boolean compruebaEmail() throws SQLException {
        boolean existe = false;
        for(Persona persona: personasRepository.findAll()){
            logger.info(persona);
            if(persona.getEmail() == txtEmailRegistro.getText()) {
                existe = true;
            }
            return existe;
        }
        return false;
    }
}
