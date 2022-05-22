package utilities;

import es.diadam.diadam.managers.ManagerBBDD;
import es.diadam.diadam.models.Producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class DataDB {
    // Insertamos un dato de prueba, aquí vemos un acoplamiento...
    // Si no quiseramos esto, podríamos usar Order de test
    // Lo ideal es meterlo en el script, e iniciarlo cada vez...
    public static void insertPaisTest(Producto productoTest) throws SQLException {
        String query = "INSERT INTO pais VALUES (null, ?, ?, ?, ?, ?, ?)";
        ManagerBBDD db = ManagerBBDD.getInstance();
        db.open();
        Optional<ResultSet> res = db.insert(query, productoTest.getNombre(), productoTest.getStock(), productoTest.getPrecio(), productoTest.getDescripcion(),productoTest.getAvatar());
        if (res.get().first()) {
            productoTest.setId(UUID.randomUUID().toString());
        }
        db.close();
    }

}
