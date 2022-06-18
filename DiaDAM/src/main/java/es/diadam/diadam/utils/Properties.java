package es.diadam.diadam.utils;

import java.io.File;

/**
 * @author Iván Azagra
 * Todos los valores introducidos en cuanto a tamaño de ventanas son temporales
 * y deberán sopesarse con el diseño de la aplicación final.
 */
public class Properties {
    // Interfaz cliente
    public static final String APP_TITLE = "DIA DAM";
    public static final int APP_HEIGHT = 450;
    public static final int APP_WIDTH = 610;
    public static final String APP_ICON = "images/Dia.png";

    // Splash de inicio
    public static final int SPLASH_HEIGHT = 300;
    public static final int SPLASH_WIDTH = 430;

    // Inicio Sesión / Registro Sesión
    public static final int INICIOSESION_HEIGHT = 400;
    public static final int INICIOSESION_WIDTH = 550;

    // Acerca de
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_AUTHORS = "Jorge, Alvaro e Iván";
    public static final String GITHUB_REPO = "Repositorio";
    public static final String GITHUB_LINK = "https://github.com/IvanAzagraTroya/DiaDAM";
    // Valores momentaneos para rellenar, cambiar conforme a gusto
    public static final int ACERCADE_HEIGHT = 350; // Cambia la altura de la aplicación
    public static final int ACERCADE_WIDTH = 480; // Cambia el ancho de la aplicación

    // Editor del admin
    public static final int PRODUCTOEDITAR_HEIGHT = 450;
    public static final int PRODUCTOEDITAR_WIDTH = 610;


    // Carrito de compra
    public static final int CARRITO_HEIGHT = 400;
    public static final int CARRITO_WIDTH = 600;

    // Editar producto
    public static final int EDITARPRODUCTO_HEIGHT = 300;
    public static final int EDITARPRODUCTO_WIDTH = 400;

    // Data
    private static final String APP_PATH = System.getProperty("user.dir");
    public static final String DATA_DIR = APP_PATH + File.separator + "data";

    public static final String BACKUP_DIR = DATA_DIR + File.separator + "backup";
    public static final String BACKUP_FILE = BACKUP_DIR + File.separator + "ventas.bak";
    public static final String IMAGES_DIR = DATA_DIR + File.separator + "images";

    // Base de datos
    
    public static final String DB_DIR = APP_PATH + File.separator + "db";
    public static final String DB_FILE = DB_DIR + File.separator + "diadam.db";
}
