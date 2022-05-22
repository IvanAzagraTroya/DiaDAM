package es.diadam.diadam.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LineaVenta {
    private final SimpleStringProperty producto;
    private final SimpleDoubleProperty precio;
    private final SimpleIntegerProperty cantidad;
    private  double total;

    public LineaVenta(String producto, double precio, int cantidad, double total) {
        this.producto= new SimpleStringProperty(producto);
        this.precio= new SimpleDoubleProperty(precio);
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }

    public String getProducto() {
        return producto.get();
    }

    public SimpleStringProperty productoProperty() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto.set(producto);
    }

    public double getPrecio() {
        return precio.get();
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public SimpleIntegerProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "lineaVenta{" +
                "producto='" + producto + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", total=" + total +
                '}';
    }
}
