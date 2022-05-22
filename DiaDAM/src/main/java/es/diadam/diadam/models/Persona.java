package es.diadam.diadam.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author Álvaro
 */
@Data
@Builder
public class Persona {
    private String id = UUID.randomUUID().toString();
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty direccion;
    private final StringProperty telefono;
    private final StringProperty tarjeta;
    private final StringProperty email;
    private final StringProperty contrasenia;
    private final StringProperty foto;
    private final StringProperty tipo;


    public Persona(String id ,String nombre, String apellido, String direccion, String telefono, String tarjeta, String email, String contrasenia, String foto, String tipo ) {
        this.id= id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion =new SimpleStringProperty(direccion) ;
        this.telefono = new SimpleStringProperty(telefono);
        this.tarjeta =new SimpleStringProperty(tarjeta) ;
        this.email = new SimpleStringProperty(email);
        this.contrasenia =new SimpleStringProperty(contrasenia) ;
        this.foto = new SimpleStringProperty(foto);
        this.tipo= new SimpleStringProperty(tipo);


    }

    public Persona(String nombre, String apellido, String direccion, String telefono, String tarjeta, String email, String contrasenia, String foto, String tipo ) {
        this.id= id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion =new SimpleStringProperty(direccion) ;
        this.telefono = new SimpleStringProperty(telefono);
        this.tarjeta =new SimpleStringProperty(tarjeta) ;
        this.email = new SimpleStringProperty(email);
        this.contrasenia =new SimpleStringProperty(contrasenia) ;
        this.foto = new SimpleStringProperty(foto);
        this.tipo= new SimpleStringProperty(tipo);

    }
    public Persona(){this(null, null, null, null, null, null, null, null,null,null);}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getTarjeta() {
        return tarjeta.get();
    }

    public StringProperty tarjetaProperty() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta.set(tarjeta);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getContrasenia() {
        return contrasenia.get();
    }

    public StringProperty contraseniaProperty() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia.set(contrasenia);
    }

    public String getFoto() {
        return foto.get();
    }

    public StringProperty fotoProperty() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto.set(foto);
    }

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre=" + nombre +
                ", apellido=" + apellido +
                ", direccion=" + direccion +
                ", telefono=" + telefono +
                ", tarjeta=" + tarjeta +
                ", email=" + email +
                ", contrasenia=" + contrasenia +
                ", foto=" + foto +
                ", tipo=" + tipo +
                '}';
    }
}
