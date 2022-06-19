package es.diadam.diadam.controllers;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Carrito;
import es.diadam.diadam.models.LineaVenta;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Venta;
import es.diadam.diadam.repositories.CarritoRepository;
import es.diadam.diadam.repositories.PersonasRepository;
import es.diadam.diadam.repositories.ProductoRepository;
import es.diadam.diadam.repositories.VentasRepository;
import es.diadam.diadam.services.Storage;
import es.diadam.diadam.utils.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarritoController {
    private static final ManagerBBDD db = ManagerBBDD.getInstance();
    private static final Storage storage = Storage.getInstance();
    private final ProductoRepository productosRepository = ProductoRepository.getInstance(db, storage);
    private final PersonasRepository personasRepository = PersonasRepository.getInstance(db);
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();
    private final ObservableList<Integer> cantidadList = FXCollections.observableArrayList();

    private String email;
    private Venta venta;

    private Stage dialogStage;
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




    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        cantidadList.addAll(1, 2, 3, 4, 5);
        // Iniciamos las vistas
        initTableView();
        // Cargamos los datos
        initData();
        //Calculamos el total del carrito
        calcularTotal();

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
    private Venta onTerminarAction(ActionEvent actionEvent) throws SQLException {

        if (carritoRepository.getItems().size() > 0) {
            System.out.println("Terminar");
            // Procesamos las ventas...
            Persona cliente = personasRepository.findByEmail(email);
            System.out.println(cliente);
            List<LineaVenta> lineasVenta = carritoRepository.getItems().stream()
                    .map(item -> new LineaVenta(item.getNombre(), item.getPrecio(), item.getCantidadProductos(), item.getTotal()))
                    .collect(Collectors.toList());
            var venta = VentasRepository.getInstance().save(lineasVenta, cliente);
            System.out.println(venta.toString());
            if (venta != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Venta realizada");
                alert.setHeaderText("Venta realizada con éxito. Total " + venta.getTotal() + " €");
                alert.setContentText("¿Desea imprimir la factura?");
                System.out.println("Venta realizada con éxito. Total " + venta.getTotal() + " €");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    imprimirFactura(venta);
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
        return venta;
    }

    private void imprimirFactura(Venta venta)  {
        String html ="";
        String pedido = venta.getCliente().getNombre();
        double total = venta.getTotal();


        try {
            FileOutputStream fos = new FileOutputStream(Properties.FACTURA_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            html =
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"es-ES\">\n" +
                            "    <head> \n" +
                            "            <title>Recibo diadam</title>\n" +
                            "            <meta charset=\"utf-8\">\n" +
                            "            <link rel=\"stylesheet\" href=\"../src/main/resources/es/diadam/diadam/styles/estilos.css\">\n" +
                            "\n" +
                            "    </head>\n" +
                            "    <body>  \n" +
                            "        <div id=\"contenedor\">\n" +
                            "\n" +
                            "            <div id=\"cabecera\">\n" +
                            "\n" +

                            "\n" +
                            "            <h1>DiaDam</h1>\n" +
                            "\n" +
                            "            </div> <!-- /Cabezera-->\n" +
                            "\n" +
                            "            <div id=\"contenido\">\n" +
                            "\n" +
                            "                \n" +
                            "                <p> IES Luis Vives</p>\n" +
                            "                <p>Numero de tienda: 4439-77-2139</p>\n" +
                            "            \n" +
                            "\n" +
                            "            </div><!-- /Contenido-->\n" +
                            "\n" +
                            "\n" +
                            "            <div id=\"contenido2\">\n" +
                            "                <table class=\"venta\">\n" +
                            "Nombre: "+
                            "                    <tr>\n" +
                            pedido + "\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td>\n" +
                            "                            <p>Subtotal:</p> \n" +
                            "                            <h2>TOTAL: </h2>\n" +
                            "                        </td>\n" +
                            "                        <td>\n" +
                            "                            <p class=\"derecha\"> " + total +"</p>\n" +
                            "                            <h3 class=\"derecha\"> "+ total +"</h3>\n" +
                            "                        </td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                <br>\n" +
                            "                \n" +
                            "            </div><!-- /Contenido-->\n" +
                            "        </div> <!-- /Contenedor-->\n" +
                            "    </body>\n" +
                            "</html>\n" +
                            "\n" +
                            "\n";
            oos.writeUTF(html);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void initData() {
        carritoTable.setItems(carritoRepository.getItems());
    }


    public void setEmail(String email) {
        this.email = email;
    }

}
