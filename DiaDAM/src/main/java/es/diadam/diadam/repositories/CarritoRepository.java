package es.diadam.diadam.repositories;

import es.diadam.diadam.models.Carrito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author Alvaro Mingo Castillo
 */
public class CarritoRepository {
    private static CarritoRepository instance;

    private final ObservableList<Carrito> items = FXCollections.observableArrayList();

    private CarritoRepository() {
    }

    public static CarritoRepository getInstance() {
        if (instance == null) {
            instance = new CarritoRepository();
        }
        return instance;
    }

    public ObservableList<Carrito> getItems() {
        return items;
    }

    public Carrito addItem(Carrito item) {
        items.add(item);
        return item;
    }

    public Carrito removeItem(Carrito item) {
        items.remove(item);
        return item;
    }

    public double getTotal() {
        return items.stream().mapToDouble(Carrito::getTotal).sum();
    }

    public void clear() {
        items.clear();
    }
}
