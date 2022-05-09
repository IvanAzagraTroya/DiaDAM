package es.diadam.diadam.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;
/**
 * @author Jorge Sánchez Berrocoso
 */
public class Producto {
    private final StringProperty nombre;
    private final ObjectProperty<Integer> stock;
    private final ObjectProperty<Double> precio;
    private final  StringProperty descripcion;
    private final StringProperty avatar;
    private String id = UUID.randomUUID().toString();

    public Producto() {
        this(null, null, null, null, null, null);
    }

    public Producto(String id,String nombre, Integer stock, Double precio, String descripcion, String avatar) {
        this.nombre = new SimpleStringProperty(nombre);
        this.stock = new SimpleObjectProperty<Integer>(stock);
        this.precio = new SimpleObjectProperty<Double>(precio);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.avatar = new SimpleStringProperty(avatar);
        this.id = id;
    }

    public Producto(String nombre, int stock, double precio, String descripcion, String avatar) {
        this.nombre = new SimpleStringProperty(nombre);
        this.stock = new SimpleObjectProperty<Integer>(stock);
        this.precio = new SimpleObjectProperty<Double>(precio);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.avatar = new SimpleStringProperty(avatar);
        this.id = id;
    }



    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Integer getStock() {
        return stock.get();
    }

    public ObjectProperty<Integer> stockProperty() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock.set(stock);
    }

    public Double getPrecio() {
        return precio.get();
    }

    public ObjectProperty<Double> precioProperty() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio.set(precio);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getAvatar() {
        return avatar.get();
    }

    public StringProperty avatarProperty() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar.set(avatar);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre=" + nombre +
                ", stock=" + stock +
                ", precio=" + precio +
                ", descripcion=" + descripcion +
                ", avatar=" + avatar +
                ", id='" + id + '\'' +
                '}';
    }
}
