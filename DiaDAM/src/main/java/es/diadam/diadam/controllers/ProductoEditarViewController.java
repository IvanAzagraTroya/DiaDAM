package es.diadam.diadam.controllers;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Jorge Sanchez Berrocoso
 */
public class ProductoEditarViewController {
    Logger logger = LogManager.getLogger(ProductoEditarViewController.class);
    private final ManagerBBDD db = ManagerBBDD.getInstance();
    private final Storage storage = Storage.getInstance();
    ProductoRepository productoRepository = ProductoRepository.getInstance(db,storage);

    @FXML
    Label stockLabel;

    @FXML
    TextField nombreTxt;

    @FXML
    TextField stockTxt;

    @FXML
    TextField precioTxt;

    @FXML
    TextField descripcionTxt;

    @FXML
    ImageView avatarImageView;


    private Stage dialogStage;
    private Producto producto;
    private boolean aceptarClicked = false;
    private boolean editarModo = false;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        logger.info("Persona asociada: " + producto);
        if (editarModo) {
            setDataInfo();
        }
        nombreTxt.requestFocus();
    }

    private void setDataInfo() {

        nombreTxt.setText(producto.getNombre());
        stockTxt.setText(producto.getStock().toString());
        precioTxt.setText(producto.getPrecio().toString());
        descripcionTxt.setText(producto.getDescripcion());
      /* 
        // La imagen
        if (!producto.getAvatar().isBlank() && Files.exists(Paths.get(producto.getAvatar()))) {
            logger.info("Cargando imagen: " + producto.getAvatar());
            Image image = new Image(new File(producto.getAvatar()).toURI().toString());
            logger.info("Imagen cargada: " + image.getUrl());
            avatarImageView.setImage(image);
        } else {
            logger.warn("No existe la imagen. Usando imagen por defecto");
            avatarImageView.setImage(new Image(Resources.get(DiaApplication.class, "images/ImagenPorDefecto.png")));
            producto.setAvatar(Resources.getPath(DiaApplication.class, "images/ImagenPorDefecto.png"));
            logger.warn("Nueva información de imagen: " + producto);
        }
*/

    }


    public void setEditarModo(boolean editarModo) {
        this.editarModo = editarModo;
        logger.info("Modo Editar: " + editarModo);
    }


    public boolean isAceptarClicked() {
        return aceptarClicked;
    }

    @FXML
    private void onAceptarAction() throws SQLException, IOException {
        logger.info("Aceptar");
        if (isInputValid()) {
            producto.setNombre(nombreTxt.getText());
            producto.setStock(Integer.parseInt(stockTxt.getText()));
            producto.setPrecio(Double.parseDouble(precioTxt.getText()));
            producto.setDescripcion(descripcionTxt.getText());
            producto.setAvatar(avatarImageView.getImage().getUrl());
            producto.setAvatar(avatarImageView.getImage().getUrl());
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
        if (stockTxt.getText() == null || stockTxt.getText().isBlank()) {
            errorMessage += "El stock no pueden estar en blanco\n";
        }
        if (precioTxt.getText() == null || precioTxt.getText().isBlank()) {
            errorMessage += "El precio no puede estar en blanco\n";
        }

        if (descripcionTxt.getText() == null || descripcionTxt.getText().isBlank()) {
            errorMessage += "La descripcion no puede estar en blanco\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de producto incorrrectos", "Existen problemas al validar.", errorMessage);
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
            // Se lo asignamos al producto...
            producto.setAvatar(file.getAbsolutePath());
            logger.info("Se ha asignado el avatar a la persona desde: " + producto.getAvatar());
        }
    }
}
