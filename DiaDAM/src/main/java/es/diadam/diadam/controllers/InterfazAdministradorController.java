package es.diadam.diadam.controllers;

import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.managers.SceneManager;
import es.diadam.diadam.models.Producto;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.utils.Properties;
import es.diadam.diadam.utils.Resources;
import es.diadam.diadam.utils.Temas;
import es.diadam.diadam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Platform;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Jorge Sanchez
 */

//Falta meter los estilos
public class InterfazAdministradorController {
    private Logger logger = LogManager.getLogger(InterfazAdministradorController.class);

    @Inject
    ProductoRepository productoRepository;

    //Tabla de productos
    @FXML
    private TableView<Producto> productosTable;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private Label nombreLabel;
    @FXML
    private Label stockLabel;

    @FXML
    private Label precioLabel;
    @FXML
    private Label descripcionLabel;

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
            setDataInfo(newValue);
        }else{
            clearDataInfo();
        }
    }

    private void setDataInfo(Producto producto){
        logger.info("Se ha seleccionado el producto: "+producto);
        nombreLabel.setText(producto.getNombre());
        stockLabel.setText(Utils.getFormattedInt(producto.getStock()));
        precioLabel.setText(Utils.getFormattedDouble(producto.getPrecio()));
        descripcionLabel.setText(producto.getDescripcion());

        // La imagen, si no existe cargamos la de por defecto, si no la que tiene
        if(!producto.getAvatar().isBlank() && Files.exists(Paths.get(producto.getAvatar()))){
            logger.info("Cargando imagen: " +producto.getAvatar());
            Image image = new Image(new File(producto.getAvatar()).toURI().toString());
            logger.info("Imagen cargada: " + image.getUrl());
            avatarImageView.setImage(image);
        }else{
            logger.warn("No existe la imagen. Usando imagen por defecto");
            avatarImageView.setImage(new Image(Resources.get(DiaApplication.class, "images/ImagenPorDefecto.png")));
            producto.setAvatar(Resources.getPath(DiaApplication.class,"images/ImagenPorDefecto.png"));
            logger.warn("Nueva informacion de imagen: "+producto);
        }



    }

    private void clearDataInfo(){
        nombreLabel.setText("");
        stockLabel.setText("");
        precioLabel.setText("");
        descripcionLabel.setText("");
    }


    @FXML
    private void onAcercaDeAction() throws IOException {
        logger.info("Se ha pulsado el Acerca de");
        //SceneManager.get().initAcercaDe();
    }




    @FXML
    public void onSalirAction() {
        logger.info("Se ha pulsado el menú salir");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setContentText("¿Quieres salir de agenda?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            backup();
            Platform.exit();
        }else {
            alert.close();
        }
    }

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
            setDataInfo(producto);
        }
    }

    @FXML
    private void onBorrarAction() {
        logger.info("Se ha pulsado accion Borrar");
        Producto p = productosTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar");
        alert.setHeaderText("¿Borrar " + p.getNombre() + "?");
        alert.setContentText("¿Está seguro/a de borrar? Esta opción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                productoRepository.delete(p);
            } catch (SQLException | IOException e) {
                logger.error("Error al Eliminar: " + e.getMessage());
            }
        }
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
            setDataInfo(producto);
        }
    }

    @FXML
    private void onEstadisticasAction() throws IOException, SQLException {
        logger.info("Se ha pulsado accion Estadisticas");
      // SceneManager.get().initEstadisticas(productoRepository.findAll());
    }



    @FXML
    private void onMenuLimpiarAction() throws IOException{
        logger.info("Se ha pulsado la accion limpiar");
        Temas.remove(this.avatarImageView);
    }

    @FXML
    private void backup() {
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
    private void loadData() throws SQLException{
        logger.info("Cargando datos");
        productosTable.setItems(productoRepository.findAll());
    }
}
