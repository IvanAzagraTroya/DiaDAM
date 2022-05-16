package es.diadam.diadam.controllers;

import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductosRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Iván Azagra
 */
public class InterfazClienteController implements Initializable {
    Logger logger = LogManager.getLogger(InterfazClienteController.class);

    ProductosRepository productosRepository = ProductosRepository.getInstance();

    @FXML
    private ListView<Producto> productoCatalog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Producto> dataList = FXCollections.observableArrayList();
        productosRepository.getAll();

        productoCatalog.setItems(dataList);
    }
}
