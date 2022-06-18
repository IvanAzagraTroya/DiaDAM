package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;

import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Temas;
import es.diadam.diadam.utils.Themes;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra
 */
public class InterfazClienteController {
    Logger logger = LogManager.getLogger(InterfazClienteController.class);

    private final ManagerBBDD db = ManagerBBDD.getInstance();
    private final Storage storage = Storage.getInstance();

    ProductoRepository productoRepository = ProductoRepository.getInstance(db, storage);

    @FXML
    TableView<Producto> tablaProducto;

    @FXML
    TableColumn<Producto, String> idColumn;

    @FXML
    TableColumn<Producto, String> nombreColumn;

    @FXML
    TableColumn<Producto, Integer> stockColumn;

    @FXML
    TableColumn<Producto, Double> precioColumn;

    @FXML
    TableColumn<Producto, String> descripcionColumn;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private ToggleButton modeButton;

    @FXML
    private void initialize() {
        // Mismo problema que antes
        //DaggerRepositoryFactory.create().inject(this);

        try {
            System.out.println("Cargando tabla");
            loadData();

        }catch(SQLException e) {
            logger.error("No se ha podido acceder al catálogo de productos");
        }

        idColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getNombre()));
        stockColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        precioColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
        descripcionColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getDescripcion()));

    }

    @FXML
    private void onAcercaDeButton() throws IOException {
        logger.info("Iniciando la ventana Acerca De");
        SceneManager.get().initAcercaDe();
    }

    @FXML
    private void onCarritoButton() throws IOException {
        logger.info("Iniciando ventana carrito");
        SceneManager.get().initCarrito();
    }

    @FXML
    public void onSalirAction() {
        logger.info("Se ha pulsado el botón salir");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Desea salir de la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Haríamos el backup si fuese admin, pero no estoy seguro si en clientes haría falta para el carrito
            //backup();
            Platform.exit();
        }else {
            alert.close();
        }
    }

    @FXML
    public void onCambioBtn() throws IOException {
        if(modeButton.isSelected()) {
            onMenuDamAction();
        }else {
            onMenuLimpiarAction();
        }
    }


    private void loadData() throws SQLException {
        logger.info("Accediendo a catálogo...");
        //productoCatalog.getItems().addAll(productoRepository.findAll());
        tablaProducto.setItems(productoRepository.findAll());

    }

    @FXML
    private void onIniciarSesionButton() throws IOException {
        logger.info("Iniciando ventana iniciar sesión");
        SceneManager.get().initIniciarSesion();
    }

    @FXML
    private void onMenuMetroAction() throws IOException {
        logger.info("Se ha pulsado accion Metro");
        Temas.set(this.avatarImageView, Themes.METRO.get());
    }

    @FXML
    public void onMenuDamAction() throws IOException {
        logger.info("Se ha pulsado accion Dam");
        Temas.set(this.avatarImageView, Themes.DAM.get());
    }

    @FXML
    private void onMenuBootstrapt3Action() throws IOException {
        logger.info("Se ha pulsado accion Bootstrap3");
        Temas.set(this.avatarImageView, Themes.BOOTSTRAPT3.get());
    }

    @FXML
    private void onMenuDark() throws IOException {
        logger.info("Se ha pulsado accion Bootstrap3");
        Temas.set(this.avatarImageView, Themes.DARKTHEME.get());
    }

    @FXML
    private void onMenuLimpiarAction() throws IOException{
        logger.info("Se ha pulsado la accion limpiar");
        Temas.remove(this.avatarImageView);
    }
}
