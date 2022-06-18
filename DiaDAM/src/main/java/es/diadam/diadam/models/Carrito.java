package es.diadam.diadam.models;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Alvaro Mingo
 */
public class Carrito {
    private final SimpleStringProperty nombre;
    private final SimpleDoubleProperty precio;
    private final SimpleIntegerProperty cantidadProductos;
    private final SimpleStringProperty imagen;
    private final SimpleDoubleProperty total;


    public Carrito(String nombre, double precio, int cantidadProductos, String imagen) {
        this.nombre = new SimpleStringProperty (nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.cantidadProductos = new SimpleIntegerProperty(cantidadProductos);
        this.imagen =  new SimpleStringProperty (imagen);
        this.total = new SimpleDoubleProperty(precio * cantidadProductos);
    }



    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
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

    public int getCantidadProductos() {
        return cantidadProductos.get();
    }

    public SimpleIntegerProperty cantidadProductosProperty() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos.set(cantidadProductos);
        this.total.set(precio.get() * cantidadProductos);
    }

    public String getImagen() {
        return imagen.get();
    }

    public SimpleStringProperty imagenProperty() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen.set(imagen);
    }
    public double getTotal() {
        return total.get();
    }
    @Override
    public String toString() {
        return "Carrito{" +
                "nombre=" + nombre +
                ", precio=" + precio +
                ", cantidadProductos=" + cantidadProductos +
                ", imagen=" + imagen +
                ", total=" + total +
                '}';
    }
}
