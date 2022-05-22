package es.diadam.diadam.controllers;
import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
// import org.apache.ibatis.io.Resources; Este import no es el necesario
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// import org.controlsfx.tools.Utils; Este import no es el necesario

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * @author Alvaro Mingo Castillo
 */
public class PersonaEditarViewController {
    Logger logger = LogManager.getLogger(PersonaEditarViewController.class);

    @FXML
    TextField nombreTxt;
    @FXML
    TextField apellidosTxt;
    @FXML
    TextField direccionTxt;
    @FXML
    TextField telefonoTxt;
    @FXML
    TextField tarjetaTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TextField contraseniaTxt;
    @FXML
    ImageView avatarImageView;

    private Stage dialogStage;
    private Persona persona;
    private boolean aceptarClicked = false;
    private boolean editarModo = false;

    // GETTERS AND SETTERS
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        logger.info("Persona asociada: " + persona);
        if (editarModo) {
            setDataInfo();
        }
        nombreTxt.requestFocus();
    }

    private void setDataInfo() {
        nombreTxt.setText(persona.getNombre());
        apellidosTxt.setText(persona.getApellido());
        direccionTxt.setText(persona.getDireccion());
        telefonoTxt.setText(persona.getTelefono());
        tarjetaTxt.setText(persona.getTarjeta());
        emailTxt.setText(persona.getEmail());
        contraseniaTxt.setText(persona.getEmail());

        if (!persona.getFoto().isBlank() && Files.exists(Paths.get(persona.getFoto()))) {
            logger.info("Cargando imagen: " + persona.getFoto());
            Image image = new Image(new File(persona.getFoto()).toURI().toString());
            logger.info("Imagen cargada: " + image.getUrl());
            avatarImageView.setImage(image);
        } else {
            logger.warn("No existe la imagen. Usando imagen por defecto");
            avatarImageView.setImage(new Image(Resources.get(DiaApplication.class, "images/person.png")));
            persona.setFoto(Resources.getPath(DiaApplication.class, "images/person.png"));
            logger.warn("Nueva información de imagen: " + persona);
        }

    }

    public void setEditarModo(boolean editarModo) {
        this.editarModo = editarModo;
        logger.info("Modo Editar: " + editarModo);
    }

    public boolean isAceptarClicked() {
        return aceptarClicked;
    }


    @FXML
    private void onAceptarAction() {
        logger.info("Aceptar");
        if (isInputValid()) {
            persona.setNombre(nombreTxt.getText());
            persona.setApellido(apellidosTxt.getText());
            persona.setDireccion(direccionTxt.getText());
            persona.setTelefono(telefonoTxt.getText());
            persona.setEmail(emailTxt.getText());
            persona.setTarjeta(tarjetaTxt.getText());
            persona.setEmail(emailTxt.getText());
            persona.setContrasenia(contraseniaTxt.getText());
            persona.setFoto(avatarImageView.getImage().getUrl());
            aceptarClicked = true;
            dialogStage.close();
        } else {
            logger.warn("Datos no validos");
        }
    }

    @FXML
    private void onCancelarAction() {
        logger.info("Has pulsado Cancelar");
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreTxt.getText() == null || nombreTxt.getText().isBlank()) {
            errorMessage += "El nombre no puede estar en blanco\n";
        }
        if (apellidosTxt.getText() == null || apellidosTxt.getText().isBlank()) {
            errorMessage += "Los apellidos no pueden estar en blanco\n";
        }
        if (direccionTxt.getText() == null || direccionTxt.getText().isBlank()) {
            errorMessage += "La direccion no puede estar en blanco\n";
        }

        if (telefonoTxt.getText() == null || telefonoTxt.getText().isBlank() || !Utils.isTelefono(telefonoTxt.getText())) {
            errorMessage += "El telefono no puede estar en blanco\n";
        }
        if (tarjetaTxt.getText() == null || tarjetaTxt.getText().isBlank() || !Utils.isTarjeta(tarjetaTxt.getText())) {
            errorMessage += "El telefono no puede estar en blanco o no es valido\n";
        }
        if (emailTxt.getText() == null || emailTxt.getText().isBlank() || !Utils.isEmail(emailTxt.getText())) {
            errorMessage += "El email no puede estar en blanco o no es válido\n";
        }

        if (contraseniaTxt.getText() == null || contraseniaTxt.getText().isBlank() ) {
            errorMessage += "La contraseña  no puede estar en blanco\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de persona incorrrectos", "Existen problemas al validar.", errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    private void onAvatarAction() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Selecciona un avatar");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png"));
        File file = filechooser.showOpenDialog(avatarImageView.getScene().getWindow());

        if (file != null) {
            logger.info("Selecciona ha seleccionado el archivo: " + file.getAbsolutePath());
            avatarImageView.setImage(new Image(file.toURI().toString()));
            // Se lo asignamos a la persona...
            persona.setFoto(file.getAbsolutePath());
            logger.info("Se ha asignado el avatar a la persona desde: " + persona.getFoto());
        }
    }
}
