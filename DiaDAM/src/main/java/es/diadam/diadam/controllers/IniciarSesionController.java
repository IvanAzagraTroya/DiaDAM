package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 * @author Iván Azagra Troya
 */

public class IniciarSesionController {
    Logger logger = LogManager.getLogger(IniciarSesionController.class);


    PersonasRepository personasRepository = PersonasRepository.getInstance();
    ObservableList<Persona> repo = FXCollections.observableArrayList();
    ManagerBBDD db = ManagerBBDD.getInstance();

    @FXML
    // Se introduce el email del usuario que se tendrá que mirar mediante regex
    private TextField txtEmail;

    @FXML
    // Contraseña del usuario
    private TextField txtContrasenia;
    
    @FXML
    private ImageView avatar;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void accionIniciar() throws SQLException, IOException {
        // Se pasan los parámetros del usuario al método
        String email = txtEmail.getText().toString();
        String contra = txtContrasenia.getText().toString();

        // Depuración
        logger.info("Email: ["+email+ "]");
        logger.info("Contraseña: ["+contra+"]");

        // Si se introducen mal los campos se muestra un mensaje de error.
        Alert alert;
        if (email.isEmpty() || contra.isEmpty() || !Utils.isEmail(email)) {
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
            if(compruebaEmail(contra, email)){
                logger.info("Se han comprobado correctamente email y contrasenia");
                // comprueba si el tipo del usuario es cliente o administrador, para así cargar la escena de edición de productos.
                if(compruebaTipo(email)){
                    logger.info("Abriendo interfaz de administrador logueado");
                    SceneManager.get().initInterfazAdministrador();
                } else {
                    // Si el tipo es de cliente se carga la escena del catalogo.
                    logger.info("Se ha iniciado sesión con éxito, cliente logueado");
                    SceneManager.get().initInterfazCliente();
                }
            }
            else{
                alert.setTitle("Ha ocurrido un error");
                alert.setContentText("Email o contraseña incorrectos");
                if(!compruebaEmail(contra, email))
                    txtEmail.requestFocus();
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

    private boolean compruebaEmail(String contra, String email) throws SQLException {
        String sql = "SELECT * FROM personas WHERE email = ? AND contraseña = ?";
        db.open();
        logger.info("Linea 165");
        var rs = db.select(sql, email, contra).orElseThrow(SQLException::new);
        while(rs.next()) {
            repo.add(new Persona(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("tarjeta"),
                    rs.getString("email"),
                    rs.getString("contraseña"),
                    rs.getString("avatar"),
                    rs.getString("tipo")
                )
            );
        }
        db.close();
        if(repo.isEmpty()) {
            logger.warn("El repositorio se encuentra vacío");
            return false;
        }
        logger.info("El repositorio tiene personas dentro");
        return true;
    }


    private boolean compruebaTipo(String email) throws SQLException {
        String sql = "SELECT tipo FROM personas WHERE email = ?";
        db.open();
        var rs = db.select(sql, email).orElseThrow(SQLException::new);
        logger.warn("Linea 195");
        while(rs.next()) {
            if (rs.getString("tipo").equals("ADMIN")) {
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }
}
