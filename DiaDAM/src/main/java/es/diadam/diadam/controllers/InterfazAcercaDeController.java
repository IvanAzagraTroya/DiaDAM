package es.diadam.diadam.controllers;

import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Utils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Veronica Anchitipan, Iván Azagra
 */

public class InterfazAcercaDeController {
    Logger logger = LogManager.getLogger(InterfazAcercaDeController.class);
    // complementos equipo
    private Stage dialogStage;

    @FXML
    private Label autores;
    @FXML
    private Label repo;
    @FXML
    private Label version;
    @FXML
    private Hyperlink hyperlink;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void initialize() {
        version.setText(Properties.APP_VERSION);
        autores.setText(Properties.APP_AUTHORS);
        hyperlink.setText(Properties.GITHUB_REPO);
        repo.setText(Properties.APP_TITLE);
        
        hyperlink.setOnAction(event -> githubAction());
    }
    
    @FXML
    private void aceptarAction() {
        dialogStage.close();
    }
    
    private void githubAction() {
        try{
             Utils.openBrowser(Properties.GITHUB_LINK);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir la página");
            alert.setContentText("No se ha podido abrir el navegador" +"\n"+
                    e.getMessage());
            logger.error("Ha ocurrido el error: ");
        }
    }
}
