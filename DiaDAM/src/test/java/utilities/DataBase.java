package utilities;

//import com.sun.tools.javac.Main;
import com.sun.tools.javac.Main;
import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.utils.ApplicationProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DataBase {
    public static void init() {
        ApplicationProperties properties = new ApplicationProperties();
        boolean init = Boolean.parseBoolean(properties.readProperty("database.initdata"));
        if (init) {
            ManagerBBDD controller = ManagerBBDD.getInstance();
            String dataPath = "sql" + File.separator + "init-db.sql";
            try {
                var sqlFile = Main.class.getClassLoader().getResource(dataPath).getPath();
                controller.open();
                controller.initData(sqlFile, false);
                controller.close();
            } catch (SQLException e) {
                System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println("Error al leer el fichero de datos iniciales: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Borra todas las tablas, que cuesta menos que tirar toda la base de datos y levantarla de nuevo
     *
     * @throws SQLException
     */
    public static void deleteAll() throws SQLException {
        ManagerBBDD db = ManagerBBDD.getInstance();
        String query = "DELETE FROM pais";
        db.open();
        db.delete(query);
        query = "DELETE FROM acuerdo";
        db.beginTransaction();
        db.delete(query);
        query = "DELETE FROM linea_acuerdo";
        db.delete(query);
        db.commit();
    }
}