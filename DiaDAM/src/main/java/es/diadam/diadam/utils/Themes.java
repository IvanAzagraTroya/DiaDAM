package es.diadam.diadam.utils;

/**
 * @author Iván Azagra
 */
public enum Themes {
    OSCURO("");
    
    private final String modo;
    
    Themes(String modo) {
        this.modo = modo;
    }
    
    public String get() {
        return modo;
    }
}
