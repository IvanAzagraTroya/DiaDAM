package es.diadam.diadam.views;

/**
 * @author Iván Azagra
 */

public enum Views {
    SPLASH("views/Splash.fxml"),
    INTERFAZCLIENTE("views/InterfazCliente-view.fxml"),
    ACERCADE("views/Acercade.fxml"),
    INTERFAZADMIN("views/Interfaz-Administrador-view.fxml"),
    INICIOSESION("views/InicioSesion.fxml"),
    REGISTROSESION("views/RegistroSesion.fxml"),
    CARRITO("views/CarritoView.fxml"),
    ESTADISTICAS("views/Estadisticas.fxml");

    private final String view;
    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
