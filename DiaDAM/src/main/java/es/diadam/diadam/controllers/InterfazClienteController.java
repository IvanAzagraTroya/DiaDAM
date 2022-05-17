package es.diadam.diadam.controllers;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;

import es.diadam.diadam.utils.Resources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra
 */
public class InterfazClienteController {
    Logger logger = LogManager.getLogger(InterfazClienteController.class);

    //No me detecta el DaggerRepositoryFactory así que de momento usaré la Inyección
    //private ProductoRepository productoRepository = DaggerRepositoryFactory.create().build();

    @Inject
    ProductoRepository productoRepository;

    @FXML
    private ListView<Producto> productoCatalog;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private void initialize() {
        // Mismo problema que antes
        //DaggerRepositoryFactory.create().inject(this);
        try {
            loadData();

        }catch(SQLException e) {
            logger.error("No se ha podido acceder al catálogo de productos");
        }
        // TODO revisar por si falta algo
    }

    private void onProductoSelected(Producto producto) {
        // TODO método que interactúe con el carrito
        // Aquí se llevaría al carrito de compra?
    }

    private void setProductInfo(Producto producto) {
        logger.info("Se ha seleccionado: "+producto+" producto");
        // TODO mirar los componentes dentro del listView
        //nombreLabel.setText(producto.getNombre());
        //descLabel.setText(producto.getDescripcion());
        // TODO revisar precioLabel en InterfazClienteController.
        //precioLabel.setText(producto.getPrecio().toString());

        if(!producto.getAvatar().isBlank() && Files.exists(Paths.get(producto.getAvatar()))) {
            logger.info("Cargando imagen: "+producto.getAvatar());
            Image image = new Image(new File(producto.getAvatar()).toURI().toString());
            logger.info("Imagen cargada: "+image.getUrl());
            avatarImageView.setImage(image);
        }else {
            logger.warn("No se ha encontrado la foto, se usará una por defecto");
            // TODO meter la imagen por defecto de Jorge para los productos
            avatarImageView.setImage(new Image(Resources.get(DiaApplication.class, "images/defectoComida.png")));
            producto.setAvatar(Resources.getPath(DiaApplication.class, "images/defectoComida.png"));
            logger.warn("Se ha establecido la imagen por defecto en el producto"+ producto);
        }
    }

    /*private void clearDataInfo() {
        algoLabel.setText("");
    }*/

    @FXML
    private void onAcercaDeButton() throws IOException {
        logger.info("Iniciando la ventana Acerca De");
        SceneManager.get().initAcercaDe();
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
    private void loadData() throws SQLException {
        logger.info("Accediendo a catálogo...");
        productoCatalog.setItems(productoRepository.findAll());
    }
}
