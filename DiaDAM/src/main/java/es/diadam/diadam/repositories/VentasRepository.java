package es.diadam.diadam.repositories;
import es.diadam.diadam.models.LineaVenta;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.models.Venta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alvaro Mingo Castillo
 */

public class VentasRepository {
    private static VentasRepository instance;
    Map<String, Venta> ventas;

    private VentasRepository() {
        ventas = new HashMap<>();
    }

    public static VentasRepository getInstance() {
        if (instance == null) {
            instance = new VentasRepository();
        }
        return instance;
    }

    public Venta save(List<LineaVenta> lineasVenta, Persona cliente) {
        System.out.println("Guardando venta...");
        Venta venta = new Venta(lineasVenta, cliente);
        ventas.put(venta.getId(), venta);
        // Simulamos esto que no es necesario
        return ventas.get(venta.getId());
    }
}
