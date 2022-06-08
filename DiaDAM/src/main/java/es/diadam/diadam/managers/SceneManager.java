package es.diadam.diadam.managers;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.controllers.*;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.views.Views;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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

/**
 * @author Iván Azagra
 */
public class SceneManager {
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

    public void initInterfazCliente() throws IOException {
        logger.info("Iniciando el catálogo");
        Platform.setImplicitExit(true);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(appClass.getResource(Views.INTERFAZCLIENTE.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.APP_WIDTH, Properties.APP_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.getIcons().add(new Image(Resources.get(DiaApplication.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        logger.info("Escena Main cargada");
        // Por si la app es cerrada
        stage.setOnCloseRequest(event -> {
            fxmlLoader.<InterfazClienteController>getController().onSalirAction();
        });
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    public void initSplash(Stage stage) throws IOException, InterruptedException {
        Platform.setImplicitExit(false);
        logger.info("Iniciando splash");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.SPLASH.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.SPLASH_WIDTH, Properties.SPLASH_HEIGHT);
        stage.getIcons().add(new Image(Resources.get(DiaApplication.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        logger.info("Escena splash cargada");
        stage.show();
    }

    public void initIniciarSesion() throws IOException {
        logger.info("Abriendo iniciar sesión");
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(DiaApplication.class.getResource(Views.INICIOSESION.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.INICIOSESION_WIDTH, Properties.INICIOSESION_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle("Inicio sesión: ");
        fxmlLoader.<IniciarSesionController>getController().setDialogStage(stage);
        IniciarSesionController controller = fxmlLoader.getController();
        logger.info("Escena Inicio Sesión cargada");
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void initRegistro() throws IOException {
        // TODO Meter funciones de botones
        logger.info("Abriendo registro usuario");
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(DiaApplication.class.getResource(Views.REGISTROSESION.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.INICIOSESION_WIDTH, Properties.INICIOSESION_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle("Registro");
        logger.info("Escena registro cargada");
        stage.setResizable(false);
        IniciarSesionController controller = fxmlLoader.getController();
        // Aquí irán los métodos a usar del controlador
        stage.showAndWait();
    }

    public void initAcercaDe() throws IOException {
        logger.info("Iniciando Acerca De");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.ACERCADE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACERCADE_WIDTH, Properties.ACERCADE_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle("Acerca De: ");
        fxmlLoader.<InterfazAcercaDeController>getController().setDialogStage(stage);
        stage.setScene(scene);
        logger.info("Escena Acerca De cargada");
        stage.showAndWait();
    }

    public void initEstadisticas(List<Producto> productos) throws IOException {
        logger.info("Iniciando estadísticas");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.ESTADISTICAS.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ESTADISTICAS_WIDTH, Properties.ESTADISTICAS_HEIGHT);
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

    public void initInterfazAdministrador() throws IOException{
        logger.info("Iniciando interfaz administrador");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.INTERFAZADMIN.get()));
    }

    public boolean initProductoEditar(boolean edicion, Producto p) throws IOException{
        logger.info("Iniciando edición de producto");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.INTERFAZADMIN.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.PRODUCTOEDITAR_WIDTH, Properties.PRODUCTOEDITAR_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(edicion ? "Editar Producto" : "Nuevo Producto");
        stage.setResizable(false);
        ProductoEditarViewController controller = fxmlLoader.getController();
        controller.setDialogStage(stage);
        controller.setEditarModo(edicion);
        controller.setProducto(p);
        stage.setScene(scene);
        logger.info("Cargada escena de edición de productos");
        stage.showAndWait();
        return controller.isAceptarClicked();

    }

    public void initCarrito() throws IOException {
        logger.info("Iniciando carrito");
        FXMLLoader fxmlLoader = new FXMLLoader(DiaApplication.class.getResource(Views.CARRITO.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.CARRITO_WIDTH, Properties.CARRITO_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        // TODO resto del init carrito, pantalla subordinada o no subordinada?
    }
}
