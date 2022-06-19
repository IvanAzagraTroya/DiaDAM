package es.diadam.diadam.dto;

import es.diadam.diadam.models.Producto;

import java.io.Serializable;

/**
 * @author Jorge Sánchez Berrocoso
 */
public class ProductoDTO implements Serializable {
    private final String id;
    private final String nombre;
    private  final Integer stock;
    private  final Double precio;
    private final String descripcion;
    private final String avatar;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.stock = producto.getStock();
        this.precio = producto.getPrecio();
        this.descripcion = producto.getDescripcion();
        this.avatar = producto.getAvatar();


    }
    public Producto fromDTO(){
        return new Producto(id, nombre, stock,precio,descripcion,avatar);
    }
}
