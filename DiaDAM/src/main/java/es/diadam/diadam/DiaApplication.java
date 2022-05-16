package es.diadam.diadam;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.managers.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iván Azagra
 */
public class DiaApplication extends Application {
    static Logger logger = LogManager.getLogger(DiaApplication.class);

    public static void main(String[] args) {
        checkServer();
        launch();
    }

    private static void checkServer() {
        logger.info("Comprobamos la conexión con el servidor");
        ManagerBBDD controller = ManagerBBDD.getInstance();

        try {
            controller.open();
            Optional<ResultSet> result = controller.select("SELECT 'Hello there'");
            if (result.isPresent()) {
                result.get().next();
                controller.close();
                logger.info("Conexión correcta con la base de datos");
            }
        }catch (SQLException e) {
            logger.error("Error al conectar con la base de datos" +e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        //Carga el SceneManager
        SceneManager sceneManager = SceneManager.getInstance(DiaApplication.class);
        sceneManager.initSplash(stage);
    }

}