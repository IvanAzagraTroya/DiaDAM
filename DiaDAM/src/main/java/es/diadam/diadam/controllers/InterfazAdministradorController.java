package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Temas;
import es.diadam.diadam.utils.Themes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Platform;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Jorge Sanchez
 */

//Falta meter los estilos
public class InterfazAdministradorController {
    private Logger logger = LogManager.getLogger(InterfazAdministradorController.class);
   private final ManagerBBDD db = ManagerBBDD.getInstance();
    private final Storage storage = Storage.getInstance();
    ProductoRepository productoRepository = ProductoRepository.getInstance(db,storage);

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    //Tabla de productos
    @FXML
    private TableView<Producto> productosTable;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

  

    @FXML
    private TextField nombreField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField precioField;

    @FXML
    private TextField descripcionField;

    @FXML
    private ImageView avatarImageView;


    @FXML
    private void initialize() {
        try{
            loadData();
        }catch(SQLException e){
            logger.error("No se ha podido cargar la lista de productos");
        }


        //Asigno la columna de la tabla
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        //Asigno los manejadores de eventos
        productosTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> onProductoSelecionado(newValue)
        );
        clearDataInfo();
        productosTable.getSelectionModel().selectFirst();
    }


    private void onProductoSelecionado(Producto newValue){
        if(newValue != null){
          onObtenerDatosProducto(newValue);
        }else{
            clearDataInfo();
        }
    }

    private void onObtenerDatosProducto(Producto a){
        if(a!=null){
            nombreField.setText(a.getNombre());
            stockField.setText(String.valueOf(a.getStock()));
            precioField.setText(String.valueOf(a.getPrecio()));
            descripcionField.setText(a.getDescripcion());
        }
    }

    private void clearDataInfo(){
        nombreField.setText("");
        stockField.setText("");
        precioField.setText("");
        descripcionField.setText("");
    }


    @FXML
    private void onAcercaDeAction() throws IOException {
        logger.info("Se ha pulsado el Acerca de");
        SceneManager.get().initAcercaDe();
    }

  /*  @FXML
    public void onSalirAction() {

        logger.info("Se ha pulsado el botón salir");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Desea salir de la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Haríamos el backup si fuese admin, pero no estoy seguro si en clientes haría falta para el carrito
            //backup();
            Platform.exit();
        }else {
            alert.close();
        }
    }*/

    @FXML
    private void onNuevoAction() throws IOException{
        logger.info("Se ha pulsado accion Nuevo");
        Producto producto = new Producto();
        boolean aceptarClicked = SceneManager.get().initProductoEditar(false, producto);
        if (aceptarClicked) {
            try{
                productoRepository.create(producto);
            }catch(SQLException | IOException e){
                logger.error("Error al crear producto: " + e.getMessage());
            }
            onObtenerDatosProducto(producto);
        }
    }

    @FXML
    public void onBorrarAction() throws IOException, SQLException {
        logger.info("Se ha pulsado accion borrar");
        Producto producto = productosTable.getSelectionModel().getSelectedItem();
        productoRepository.delete(producto);
    }

    @FXML
    private void onEditarAction() throws IOException {
        logger.info("Se ha pulsado accion Editar");
        Producto producto = productosTable.getSelectionModel().getSelectedItem();
        boolean aceptarClicked = SceneManager.get().initProductoEditar(true, producto);
        if (aceptarClicked) {
            try {
                productoRepository.update(producto);
            } catch (SQLException | IOException e) {
                logger.error("Error al actualizar producto: " + e.getMessage());
            }
            onObtenerDatosProducto(producto);
        }
    }


    @FXML
    private void onMenuLimpiarAction() throws IOException{
        logger.info("Se ha pulsado la accion limpiar");
        Temas.remove(this.avatarImageView);
    }

    @FXML
    private void backup(){
        try {
            productoRepository.backup();
            logger.info("Backup realizado en: " + Properties.BACKUP_DIR);
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error al hacer backup");
            error.setContentText("No se ha podido hacer el backup");
            logger.error("No se ha podido hacer el backup", e.getMessage());
            error.showAndWait();
        }

    }

    @FXML
    private void restaurar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restaurar Backup");
        alert.setContentText("¿Está seguro de restaurar el backup?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                productoRepository.restore();
                loadData();
                logger.info("Backup restaurado desde: " + Properties.BACKUP_DIR);
            } catch (ClassNotFoundException | SQLException | IOException e){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error al restaurar");
                error.setContentText("No se ha podido restaurar");
                logger.error("No se ha podido restaurar", e.getMessage());
                error.showAndWait();
            }
        }
    }

    @FXML
    private void onMenuMetroAction() throws IOException {
        logger.info("Se ha pulsado accion Metro");
        Temas.set(this.avatarImageView, Themes.METRO.get());
    }

    @FXML
    public void onMenuDamAction() throws IOException {
        logger.info("Se ha pulsado accion Dam");
        Temas.set(this.avatarImageView, Themes.DAM.get());
    }

    @FXML
    private void onMenuBootstrapt3Action() throws IOException {
        logger.info("Se ha pulsado accion Bootstrap3");
        Temas.set(this.avatarImageView, Themes.BOOTSTRAPT3.get());
    }

    @FXML
    private void onMenuDark() throws IOException {
        logger.info("Se ha pulsado accion Bootstrap3");
        Temas.set(this.avatarImageView, Themes.DARKTHEME.get());
    }


    @FXML
    private void loadData() throws SQLException{
        logger.info("Cargando datos");
        productosTable.setItems(productoRepository.findAll());
    }
}
