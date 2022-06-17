package es.diadam.diadam.utils;

/**
 * @author Iván Azagra
 */
public enum Themes {
    METRO("styles/metrodark.css"),
    DAM("styles/dam.css"),
    DARKTHEME("styles/DarkTheme.css"),
    BOOTSTRAPT3("styles/bootstrapt3.css");
    
    private final String modo;
    
    Themes(String modo) {
        this.modo = modo;
    }
    
    public String get() {
        return modo;
    }
}
