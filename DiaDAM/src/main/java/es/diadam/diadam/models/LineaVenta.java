package es.diadam.diadam.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LineaVenta {
    private final String producto;
    private final double precio;
    private final int cantidad;
    private final double total;

    public LineaVenta(String producto, double precio, int cantidad, double total) {
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double getTotal() {
        return total;
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
