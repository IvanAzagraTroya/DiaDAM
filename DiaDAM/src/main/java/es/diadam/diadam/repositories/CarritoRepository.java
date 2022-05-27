package es.diadam.diadam.repositories;

import es.diadam.diadam.models.Carrito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void addItem(Carrito item) {
        items.add(item);
    }

    public void removeItem(Carrito item) {
        items.remove(item);
    }

    public double getTotal() {
        return items.stream().mapToDouble(Carrito::getTotal).sum();
    }

    public void clear() {
        items.clear();
    }

}
