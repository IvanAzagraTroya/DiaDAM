package es.diadam.diadam.controllers;


import es.diadam.diadam.utils.Properties;
import javafx.fxml.FXML;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Veronica Anchitipan
 */

public class InterfazAcercaDeController {
    //push comentario
    Logger logger = LogManager.getLogger(InterfazAcercaDeController.class);
    // complementos equipo
    private Stage dialogStage;

    @FXML
    private Label nombres;
    @FXML
    private Label repositorio;
    @FXML
    private Label version;
    @FXML
    private Hyperlink githubLink;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void initialize() {

        version.setText(Properties.APP_VERSION);
        nombres.setText(Properties.APP_AUTHORS);
        githubLink.setText(Properties.APP_TITLE);
        repositorio.setText(Properties.APP_TITLE);
    }
}
