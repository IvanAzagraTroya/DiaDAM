package es.diadam.diadam.views;

import java.io.File;

public enum Views {
    SPLASH("views"+ File.separator +"splash-view.fxml"),
    INTERFAZCLIENTE("views"+ File.separator+ "interfazCliente-view.fxml"),
    INTERFAZADMIN("views"+ File.separator+ "interfaz-Administrador-view.fxml"),
    ACERCADE("views"+ File.separator+"acercade-view.fxml"),
    PRODUCTOEDITAR("views"+ File.separator+"productoeditar-view.fxml"),
    INICIOSESION("views"+ File.separator+"inicioSesion-view.fxml"),
    REGISTROSESION("views"+ File.separator+"registroSesion.fxml"),
    CARRITO("views"+ File.separator+"carritoView.fxml"),
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
