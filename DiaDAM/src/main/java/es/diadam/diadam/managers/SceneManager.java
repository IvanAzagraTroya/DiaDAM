package es.diadam.diadam.managers;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.controllers.EstadisticasController;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.views.Views;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static es.diadam.diadam.utils.Properties.*;

/**
 * @author Iván Azagra
 */
public class SceneManager {
    // TODO todo el manager de las transiciones
    private static SceneManager instance;
    private final Class<?> appClass;
    Logger logger = LogManager.getLogger(SceneManager.class);

    private Stage mainStage;

    private SceneManager(Class<?> appClass) {
        this.appClass = appClass;
        logger.info("SceneManager creado");
    }

    // Singleton
    public static SceneManager getInstance(Class<?> appClass) {
        if (instance == null) {
            instance = new SceneManager(appClass);
        }
        return instance;
    }

    public static SceneManager get() {
        return instance;
    }

    public void changeScene(Node node, Views view) throws IOException {
        logger.info("Cargando la escena " +view.get());
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(appClass.getResource(view.get())));
        Scene newScene = new Scene(root, Properties.APP_WIDTH, Properties.ACERCADE_HEIGH);
        logger.info("Escena: "+view.get() + " cargada");
        stage.setScene(newScene);
        stage.show();
    }

    public void initInterfazCliente() throws IOException {
        logger.info("Iniciando el catálogo");
        Platform.setImplicitExit(true);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(appClass.getResource(Views.INTERFAZCLIENTE.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.APP_WIDTH, Properties.APP_HEIGH); // TODO mirar APP WIDTH Y APP HEIGH
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.getIcons().add(new Image(Resources.get(DiaApplication.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        logger.info("Escena Main cargada");
        // Por si la app es cerrada
        stage.setOnCloseRequest(event -> {
            // TODO realizar la acción de salir asociada a un botón
           //fxmlLoader.getController().onSalirAction();
        });
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    public void initSplash(Stage stage) throws IOException, InterruptedException {
        Platform.setImplicitExit(false);
        logger.info("Iniciando splash");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.SPLASH.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.SPLASH_WIDTH, Properties.SPLASH_HEIGH);
        stage.getIcons().add(new Image(Resources.get(DiaApplication.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        logger.info("Escena splash cargada");
        stage.show();
    }

    public void initAcercaDe() throws IOException {
        logger.info("Iniciando Acerca De");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.ACERCADE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACERCADE_WIDTH, Properties.ACERCADE_HEIGH);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Acerca De: ");
        stage.setScene(scene);
        logger.info("Scene Acerca De cargada");
        stage.showAndWait();
    }

    // TODO EDITAR CATALOGO

    public void initEstadisticas(List<Producto> productos) throws IOException {
        logger.info("Iniciando estadísticas");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.ESTADISTICAS.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ESTADISTICAS_WIDTH, Properties.ESTADISTICAS_HEIGH);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        // Como esta pantalla es subordinada necesita que le especifiquen su dueño
        stage.initOwner(mainStage);
        stage.setTitle("Estadísticas:");
        stage.setResizable(false);
        EstadisticasController controller = fxmlLoader.getController();
        // TODO hacer el set producto dentro de estadísticas controller
        //controller.setProductoData(productos);
        stage.setScene(scene);
        logger.info("Escena de estadísticas cargada");
        stage.showAndWait();
    }

    public void initCarrito() throws IOException {
        logger.info("Iniciando carrito");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.CARRITO.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.CARRITO_WIDTH, Properties.CARRITO_HEIGH);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        // TODO resto del init carrito, pantalla subordinada o no subordinada?
    }

    public void initProductoEditar() throws IOException {

    }
}
