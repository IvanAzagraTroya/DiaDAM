package es.diadam.diadam.views;

/**
 * @author Iván Azagra
 */

public enum Views {
    SPLASH("views/Splash"),
    INTERFAZCLIENTE("views/InterfazCliente-view.fxml"),
    ACERCADE("views/AcercaDe"),
    INTERFAZADMIN("views/Interfaz-Administrador-view"),
    INICIOSESION("views/InicioSesion"),
    REGISTROSESION("views/RegistroSesion"),
    CARRITO("views/CarritoView"),
    ESTADISTICAS("views/Estadisticas");

    private final String view;
    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
