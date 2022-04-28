package es.diadam.diadam.models;

import lombok.Builder;
import lombok.Data;

/**
 * @Author Iván Azagra
 * @version 1.0
 * Clase constructor que representa al Administrador del sistema
 */
@Data
@Builder
public class Admin extends Persona{
    private String nombre = "Admin";
    private String contrasenia = "Admin";
}
