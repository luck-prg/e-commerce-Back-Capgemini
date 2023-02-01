package com.capgeminiTp.sva.models.entities.carrito;

import com.capgeminiTp.sva.models.entities.carrito.Carrito;
import com.capgeminiTp.sva.models.entities.producto.ProductoBase;
import com.capgeminiTp.sva.models.entities.producto.ProductoPersonalizado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productoP_id", referencedColumnName = "id")
    private ProductoPersonalizado productoPersonalizado;

    @Column(name = "cantidad", columnDefinition = "INTEGER")
    private int cantidad;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrito_id", referencedColumnName = "id")
    private Carrito carrito = new Carrito();


    //constructor

    public Item() {}

    public Item(ProductoPersonalizado productoPersonalizado, Integer cantidad, Carrito carrito) {
        this.productoPersonalizado = productoPersonalizado;
        this.cantidad = cantidad;
        this.carrito = carrito;
    }

    public Item(ProductoPersonalizado productoPersonalizado, Integer cantidad){
        this.productoPersonalizado = productoPersonalizado;
        this.cantidad = cantidad;
    }

    // Getter´s and Setter´s

    public ProductoPersonalizado getProducto() {
        return productoPersonalizado;
    }

    public void setProducto(ProductoPersonalizado productoPersonalizado) {
        this.productoPersonalizado = productoPersonalizado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
