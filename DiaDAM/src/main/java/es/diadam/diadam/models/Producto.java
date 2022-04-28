package es.diadam.diadam.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @Author Iván Azagra
 * @version 1.0
 * Clase constructor para los productos
 */

@Data
@Builder
public class Producto {
    private UUID uuid;
    private int cantidad;
    private String nombre;
    private Double precio;
    private String fotoPath;
    private String descripcion;
}
