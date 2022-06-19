package es.diadam.diadam.utils;

import es.diadam.diadam.DiaApplication;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.bootstrapfx.BootstrapFX;

/**
 * @author Jorge Sanchez
 */

public class Temas {
    static Logger logger = LogManager.getLogger(Temas.class);

    public static void set(Node node, String style) {
        node.getScene().getRoot().getStylesheets().clear();
        var newStyle = DiaApplication.class.getResource(style).toString();
        node.getScene().getRoot().getStylesheets().add(newStyle);
        logger.info("Estilo: " + newStyle);
    }

    public static void remove(Node node) {
        node.getScene().getRoot().getStylesheets().clear();
        logger.info("Estilo: " + "sin estilo");
    }

    public static void changeBootstrapFX(Node node) {
        node.getScene().getRoot().getStylesheets().clear();
        node.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        System.out.println("Estilo: BootstraptFX");
        logger.info("Estilo: BootstraptFX");
    }
}
