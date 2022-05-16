package es.diadam.diadam.views;

/**
 * @author Iván Azagra
 */
public enum Views {
    SPLASH(""),
    INTERFAZCLIENTE("views/InterfazCliente-view.fxml"),
    ACERCADE(""),
    INTERFAZADMIN(""),
    INICIOSESION(""),
    CARRITO(""),
    ESTADISTICAS("");

    private final String view;
    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
