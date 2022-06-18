package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final ManagerBBDD db = ManagerBBDD.getInstance();
    
    PersonasRepository personasRepository = PersonasRepository.getInstance(db);

    ObservableList<Persona> repo = FXCollections.observableArrayList();
    private String id = UUID.randomUUID().toString();
    
    @FXML
    private TextField txtEmailRegistro;

    @FXML
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

        Alert alert;
        if (emailRegistro.isEmpty() || contraRegistro.isEmpty() || !Utils.isEmail(txtEmailRegistro.getText()) || personasRepository.compruebaEmail(emailRegistro)) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uno de los campos está vacío.");
            alert.setContentText("Asegurese de introducir email y contraseña");
            if(emailRegistro.isEmpty() || personasRepository.compruebaEmail(emailRegistro)){
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
           
            newPersona.setId(id);
            newPersona.setNombre(nombre.trim());
            newPersona.setApellido(apellidos);
            newPersona.setDireccion(direccion);
            newPersona.setTelefono(telefono);
            newPersona.setTarjeta(tarjeta);
            newPersona.setEmail(emailRegistro.trim());
            newPersona.setContrasenia(contraRegistro.trim());
            newPersona.setFoto("data"+File.separator+"images"+File.separator+newPersona.getId());
            if (newPersona.getNombre().equalsIgnoreCase("ADMIN")) {
                newPersona.setTipo("ADMIN");
                personasRepository.create(newPersona);
            } else {
                newPersona.setTipo("CLIENTE");
                personasRepository.create(newPersona);
            }

            System.out.println(newPersona);
            
        }
        alert.showAndWait();
        dialogStage.close();
    }
        
        
    @FXML
    private void accionCrearUsuariobtn(ActionEvent event) throws SQLException, IOException {
        logger.info("Se ha creado un nuevo usuario");
        accionRegistrarse();
    }

    @FXML
    private void accionAtrasBtn(ActionEvent event) {
        try{
            accionAtras();
        }catch(IOException e){
            logger.error("Error al cerrar la ventana");
        }

    }

    private void  accionAtras() throws IOException{
        SceneManager.get().initIniciarSesion();
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
    

}
