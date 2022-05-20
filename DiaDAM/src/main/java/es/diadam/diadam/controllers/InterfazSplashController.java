package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.SceneManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Veronica Anchitipan
 */

public class InterfazSplashController implements Initializable {
    Logger logger = LogManager.getLogger(InterfazSplashController.class);
    @FXML
    private ImageView fondo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // imagen-fondo-animación
        FadeTransition transition = new FadeTransition(Duration.millis(3000), fondo);
        transition.setFromValue(1.0);
        transition.setToValue(1.0);
        transition.play();

        transition.setOnFinished(event -> {
            // cierre
            Stage ventana = (Stage) fondo.getScene().getWindow();
            ventana.hide();
            // Visualización ventana principal
            SceneManager sceneManager = SceneManager.get();
            try {
                sceneManager.initSplash(ventana);
            } catch (IOException | InterruptedException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }


        });

    }

}
















