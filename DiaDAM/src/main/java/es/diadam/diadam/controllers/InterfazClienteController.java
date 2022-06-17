package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;

import es.diadam.diadam.services.Storage;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra
 */
public class InterfazClienteController {
    Logger logger = LogManager.getLogger(InterfazClienteController.class);


    ManagerBBDD db;
    Storage storage;



    ProductoRepository productoRepository = ProductoRepository.getInstance();


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

    private void onProductoSelected(Producto producto) {
        // TODO método que interactúe con el carrito
        // Aquí se llevaría al carrito de compra?

    }

/*
    private void setProductInfo(Producto producto) {
        logger.info("Se ha seleccionado: "+producto+" producto");
        //nombreLabel.setText(producto.getNombre());
        //descLabel.setText(producto.getDescripcion());
        //precioLabel.setText(producto.getPrecio().toString());

        if(!producto.getAvatar().isBlank() && Files.exists(Paths.get(producto.getAvatar()))) {
            logger.info("Cargando imagen: "+producto.getAvatar());
            Image image = new Image(new File(producto.getAvatar()).toURI().toString());
            logger.info("Imagen cargada: "+image.getUrl());
            avatarImageView.setImage(image);
        }else {
            logger.warn("No se ha encontrado la foto, se usará una por defecto");
            avatarImageView.setImage(new Image(Resources.get(DiaApplication.class, "images/defectoComida.png")));
            producto.setAvatar(Resources.getPath(DiaApplication.class, "images/ImagenPorDefecto.png"));
            logger.warn("Se ha establecido la imagen por defecto en el producto"+ producto);
        }


 */

    /*private void clearDataInfo() {
        algoLabel.setText("");
    }*/

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
    private void onChangeMode() {
        logger.info("Cambiando paleta de colores");
        // TODO meter los estilos aquí
    }
}
