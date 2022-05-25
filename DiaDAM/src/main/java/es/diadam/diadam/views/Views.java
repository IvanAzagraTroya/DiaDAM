package es.diadam.diadam.views;

public enum Views {
    SPLASH("es/diadam/diadam/views/splash-view.fxml"),
    INTERFAZCLIENTE("es/diadam/diadam/views/InterfazCliente-view.fxml"),
    INTERFAZADMIN("es/diadam/diadam/views/Interfaz-Administrador-view.fxml"),
    MAIN("es/diadam/diadam/views/agenda-view.fxml"),
    ACERCADE("es/diadam/diadam/views/acercade-view.fxml"),
    PRODUCTOEDITAR("es/diadam/diadam/views/productoeditar-view.fxml"),
    INICIOSESION("es/diadam/diadam/views/InicioSesion-view.fxml"),
    REGISTROSESION("es/diadam/diadam/views/RegistroSesion.fxml"),
    CARRITO("es/diadam/diadam/views/CarritoView.fxml"),
    PERSONAEDITAR("es/diadam/diadam/views/personaeditar-view.fxml"),
    ESTADISTICAS("es/diadam/diadam/views/estadisticas-view.fxml");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
