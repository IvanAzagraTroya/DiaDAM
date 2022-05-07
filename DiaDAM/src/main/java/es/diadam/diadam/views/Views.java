package es.diadam.diadam.views;

/**
 * @author Iván Azagra
 */
public enum Views {
    //SPLASH(),
    INTERFAZCLIENTE("views/InterfazCliente-view.fxml");
    /*INTERFAZADMIN(),
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
