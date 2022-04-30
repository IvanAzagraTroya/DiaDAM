package es.diadam.diadam.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @Author Iván Azagra
 * @version 1.0
 * Clase constructor que representa a las personas y de la que beben Admin y CLiente
 */
@Data
@Builder
public class Persona {
    private UUID uuid;
    private String nombre;
    private String email;
    private String contrasenia;
    private String fotoPath;
    private boolean esSocio;
}
