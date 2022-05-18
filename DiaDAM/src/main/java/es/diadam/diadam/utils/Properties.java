package es.diadam.diadam.utils;

import java.io.File;

/**
 * @author Iván Azagra
 * Todos los valores introducidos en cuanto a tamaño de ventanas son temporales
 * y deberán sopesarse con el diseño de la aplicación final.
 */
public class Properties {
    public static final String APP_TITLE = "DIA DAM";
    public static final int APP_HEIGH = 450;
    public static final int APP_WIDTH = 850;
    public static final String APP_ICON = "";

    // Splash de inicio
    public static final int SPLASH_HEIGH = 300;
    public static final int SPLASH_WIDTH = 480;

    // Acerca de
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_AUTHORS = "Jorge, Álvaro, Veronica e Iván";
    public static final String GITHUB_REPO = "https://github.com/IvanAzagraTroya/DiaDAM/tree/main/DiaDAM/src/main";
    // Valores momentaneos para rellenar, cambiar conforme a gusto
    public static final int ACERCADE_HEIGH = 260; // Cambia la altura de la aplicación
    public static final int ACERCADE_WIDTH = 480; // Cambia el ancho de la aplicación

    // Editor del admin
    public static final int PRODUCTOEDITAR_HEIGH = 420;
    public static final int PRODUCTOEDITAR_WIDTH = 800;

    // Ventana de estadísticas
    public static final int ESTADISTICAS_HEIGH = 400;
    public static final int ESTADISTICAS_WIDTH = 600;

    // Carrito de compra
    public static final int CARRITO_HEIGH = 400;
    public static final int CARRITO_WIDTH = 600;

    // Data
    private static final String APP_PATH = System.getProperty("user.dir");
    public static final String DATA_DIR = APP_PATH + File.separator + "data";
    // Backup para el administrador? Podría exportar el número de ventas de las estadísticas
    // a un archivo json externo
    public static final String BACKUP_DIR = DATA_DIR + File.separator + "backup";
    // Pongo ventas pensando en la orientación a backup de administrador, tendría sentido
    public static final String BACKUP_FILE = BACKUP_DIR + File.separator + "ventas.bak";
    public static final String IMAGES_DIR = DATA_DIR + File.separator + "images";

    // Base de datos
    public static final String DB_DIR = APP_PATH + File.separator + "db";
    public static final String DB_FILE = DB_DIR + File.separator + "diadam.sqlite";
}
