package es.diadam.diadam.dto;

import es.diadam.diadam.models.Persona;

import java.util.UUID;

public class PersonaDTO {
    private String id = UUID.randomUUID().toString();
    private final String nombre;
    private final String apellido;
    private final String direccion;
    private final String telefono;
    private final String tarjeta;
    private final String email;
    private final String contrasenia;
    private final String foto;
    private final String tipo;

    public PersonaDTO(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellido=persona.getApellido();
        this.direccion=persona.getDireccion();
        this.telefono=persona.getTelefono();
        this.tarjeta=persona.getTarjeta();
        this.email = persona.getEmail();
        this.contrasenia = persona.getContrasenia();
        this.foto = persona.getFoto();
        this.tipo=persona.getTipo();
    }

    public Persona fromDTO() {
        return new Persona(id, nombre,apellido,direccion,telefono,tarjeta, email, contrasenia, foto,tipo);
    }
}
