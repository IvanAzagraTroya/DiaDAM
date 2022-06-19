package es.diadam.diadam.models;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Alvaro Mingo Castillo
 */
public class Venta {
    private final String id = UUID.randomUUID().toString();
    private final String createdAt = LocalDateTime.now().toString();
    private final Persona cliente;
    private final List<LineaVenta> lineasVenta;
    private final double total;

    public Venta(List<LineaVenta> lineasVenta, Persona cliente) {
        this.lineasVenta = lineasVenta;
        this.cliente = cliente;
        this.total = getTotal();
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<LineaVenta> getLineasVenta() {
        return lineasVenta;
    }

    public Persona getCliente() {
        return cliente;
    }

    public double getTotal() {
        return lineasVenta.stream().mapToDouble(LineaVenta::getTotal).sum();
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", cliente=" + cliente +
                ", lineasVenta=" + lineasVenta +
                ", total=" + total +
                '}';
    }
}
