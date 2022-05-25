package es.diadam.diadam.views;

import java.io.File;

public enum Views {
    SPLASH("views"+ File.separator +"splash-view.fxml"),
    INTERFAZCLIENTE("views"+ File.separator+ "InterfazCliente-view.fxml"),
    INTERFAZADMIN("views"+ File.separator+ "Interfaz-Administrador-view.fxml"),
    MAIN("views"+ File.separator+"agenda-view.fxml"),
    ACERCADE("views"+ File.separator+"acercade-view.fxml"),
    PRODUCTOEDITAR("views"+ File.separator+"productoeditar-view.fxml"),
    INICIOSESION("views"+ File.separator+"InicioSesion-view.fxml"),
    REGISTROSESION("views"+ File.separator+"RegistroSesion.fxml"),
    CARRITO("views"+ File.separator+"CarritoView.fxml"),
    PERSONAEDITAR("views"+ File.separator+"personaeditar-view.fxml"),
    ESTADISTICAS("views"+ File.separator+"estadisticas-view.fxml");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
