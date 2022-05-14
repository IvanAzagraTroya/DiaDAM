package es.diadam.diadam.controllers;

import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductosRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Iván Azagra
 */
public class InterfazClienteController {
    Logger logger = LogManager.getLogger(InterfazClienteController.class);

    ProductosRepository productosRepository = ProductosRepository.getInstance();

    // Grid de los productos
    @FXML
    private Label nombreProducto;
    @FXML
    private Label descripcionProducto;
    @FXML
    private Label precioProducto;
}
