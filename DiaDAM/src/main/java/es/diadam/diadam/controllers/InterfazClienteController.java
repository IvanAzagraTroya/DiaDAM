package es.diadam.diadam.controllers;

import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.sql.SQLException;

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
    private void initialize() {
        //DaggerRepositoryFactory.create().inject(this);
    }
}
