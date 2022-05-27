package es.diadam.diadam.services;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface Backup<T> {

    void backup(List<T> data) throws IOException;

    ObservableList<T> restore() throws IOException;
}
