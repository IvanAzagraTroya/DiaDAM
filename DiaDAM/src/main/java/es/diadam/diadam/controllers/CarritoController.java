package es.diadam.diadam.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.diadam.diadam.DiaApplication;
import es.diadam.diadam.models.*;
import es.diadam.diadam.repositories.CarritoRepository;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.repositories.VentasRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarritoController {

    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();
    private final ObservableList<Integer> cantidadList = FXCollections.observableArrayList();
    @FXML
    private ListView<Producto> listProductos;
    @FXML
    private ListView<Producto> listProductosOferta;
    @FXML
    private TableView<Carrito> carritoTable;
    @FXML
    private TextField txtTotal;
    @FXML
    private TableColumn<Carrito, String> imagenColumn;
    @FXML
    private TableColumn<Carrito, String> productoColumn;
    @FXML
    private TableColumn<Carrito, Double> precioColumn;
    @FXML
    private TableColumn<Carrito, Integer> cantidadColumn;

    @FXML
    private void initialize() {
        cantidadList.addAll(1, 2, 3, 4, 5);
        // Iniciamos las vistas
        initProductosView();
        initTableView();
        // Cargamos los datos
        initData();

    }

    private void initData() {
        try {
            listProductos.setItems(productosRepository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        carritoTable.setItems(carritoRepository.getItems());
    }


    @FXML
    private void onEliminarAction(ActionEvent actionEvent) {
        Carrito item = carritoTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            carritoRepository.removeItem(item);
            carritoTable.refresh();
            calcularTotal();
        }
    }

    @FXML
    private void onTerminarAction(ActionEvent actionEvent) {
        if (carritoRepository.getItems().size() > 0) {
            System.out.println("Terminar");
            // Procesamos las ventas...
            Persona cliente = new Persona();
            List<LineaVenta> lineasVenta = carritoRepository.getItems().stream()
                    .map(item -> new LineaVenta(item.getNombre(), item.getCantidadProductos(), (int) item.getPrecio(), item.getTotal()))
                    .collect(Collectors.toList());
            Venta venta = VentasRepository.getInstance().save(lineasVenta, cliente);
            if (venta != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Venta realizada");
                alert.setHeaderText("Venta realizada con éxito. Total " + venta.getTotal() + " €");
                alert.setContentText("¿Desea imprimir la factura?");
                System.out.println("Venta realizada con éxito. Total " + venta.getTotal() + " €");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    System.out.println(gson.toJson(venta));
                    carritoRepository.clear();
                    carritoTable.refresh();
                    calcularTotal();
                } else {
                    System.out.println("Cancelado");
                }

            }
        } else {
            System.out.println("No hay productos en el carrito");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No hay productos en el carrito");
            alert.setContentText("Por favor, añada productos al carrito");
            alert.showAndWait();
        }
    }

    private void initProductosView() {
        // Factoria de celdas
        listProductos.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    Label nombre = new Label(item.getNombre());
                    nombre.setStyle("-fx-font-weight: bold");
                    Label precio = new Label(item.getPrecio() + " €");
                    vbox.getChildren().addAll(nombre, precio);
                    // Imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "img" + File.separator + item.getAvatar());
                    //System.out.println(dirImage);
                    imageView.setImage(new Image(dirImage.toUri().toString()));
                    // Boton
                    Button button = new Button("Añadir");
                    button.setOnAction(event -> {
                        añadirProducto(item);
                    });
                    var iconPath = DiaApplication.class.getResource("icons" + File.separator + "add-cart.png");
                    Image icon = new Image(iconPath.toString());
                    ImageView iconView = new ImageView(icon);
                    iconView.setFitHeight(20);
                    iconView.setFitWidth(20);
                    button.setGraphic(iconView);
                    button.setStyle("-fx-background-color: #9cb7e5");
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox, button);

                    // Se lo asignamos al list cell
                    setGraphic(hBox);
                }
            }
        });

        listProductos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Producto producto = listProductos.getSelectionModel().getSelectedItem();
                añadirProducto(producto);
            }
        });
    }

    private void añadirProducto(Producto item) {
        System.out.println("Añadir producto");
        System.out.println(item);
        Carrito carritoItem = new Carrito(item.getNombre(),  item.getPrecio(),1,item.getAvatar());
        carritoRepository.addItem(carritoItem);
        carritoTable.refresh(); // Forzamos que se ha refrescado la tabla
        carritoTable.getSelectionModel().select(carritoItem);
        calcularTotal();
    }

    private void calcularTotal() {
        txtTotal.setText(carritoRepository.getTotal() + " €");
    }



    private void initTableView() {
        System.out.println("Inicializando columnas...");
        carritoTable.setEditable(false);
        productoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        // Celdas personalizadas
        cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProductosProperty().asObject());
        setCantidadCell();
        //imagenColumn.setPrefWidth(50); // Puedo cambiar esto sobre la marcha
        imagenColumn.setCellValueFactory(cellData -> cellData.getValue().imagenProperty());
        setImageCell();
    }

    private void setImageCell() {
        imagenColumn.setCellFactory(param -> new TableCell<>() {
            // Creo la vista del elemento
            @Override
            public void updateItem(String item, boolean empty) {
                if (item != null) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "img" + File.separator + item);
                    //System.out.println(dirImage);
                    imageView.setImage(new Image(dirImage.toUri().toString()));
                    // Aplicamos todos los elementos gráficos a la celda
                    setGraphic(imageView);
                }
            }
        });
    }

    private void setCantidadCell() {
        cantidadColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                if (item != null) {
                    ChoiceBox choice = new ChoiceBox(cantidadList);
                    choice.getSelectionModel().select(cantidadList.indexOf(item));
                    // Aplicamos todos los elementos gráficos a la celda
                    choice.setOnAction(event -> {
                        var cantidad = (Integer) choice.getSelectionModel().getSelectedItem();
                        var carritoItem = getTableView().getItems().get(getIndex());
                        carritoItem.setCantidadProductos(cantidad);
                        calcularTotal();
                    });
                    setGraphic(choice);

                }
            }
        });
    }
}
