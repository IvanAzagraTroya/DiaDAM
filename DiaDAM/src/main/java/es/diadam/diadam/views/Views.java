package es.diadam.diadam.views;

/**
 * @author Iván Azagra
 */
public enum Views {
    //SPLASH(),
    INTERFAZCLIENTE("views/InterfazCliente-view.fxml");
    /*Acercade();
    INTERFAZADMIN(),
    INICIOSESION(),
    CARRITO();*/

    private final String view;
    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
