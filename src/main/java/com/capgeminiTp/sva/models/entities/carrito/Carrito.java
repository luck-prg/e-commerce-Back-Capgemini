package com.capgeminiTp.sva.models.entities.carrito;

import com.capgeminiTp.sva.converts.LocalDateToDateConverter;
import com.capgeminiTp.sva.models.entities.Tienda;
import com.capgeminiTp.sva.models.entities.producto.Personalizacion;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
@Getter @Setter
public class Carrito{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "estado")
    @Enumerated(value = EnumType.STRING)
    private EstadoCarrito estado;

    @Convert(converter = LocalDateToDateConverter.class)
    private LocalDate fechaApertura;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "comprador_id", referencedColumnName = "id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "tineda_id", referencedColumnName = "id")
    private Tienda tiendaVendedor;

    @OneToMany(mappedBy = "carrito", cascade = {CascadeType.ALL})
    private List<Item> productos = new ArrayList<Item>();;

    //carrito

    public Carrito(){}

    public Carrito(EstadoCarrito estado, Usuario comprador, Tienda tiendaVendedor) {
        this.estado = estado;
        this.comprador = comprador;
        this.tiendaVendedor = tiendaVendedor;
    }

    public Carrito(EstadoCarrito estado, Usuario comprador) {
        this.estado = estado;
        this.comprador = comprador;
    }

    //metodos

    public void agregarProductoAlCarrito(Item producto){
        this.productos.add(producto);
    }

    public void quitarProducto(){}

    public void comprar(){}

    public void cancelarCompra(){}

    private void analizarEstadoCarrito(){};

    private void calcularMontoTotal(){}

    // aGetter´s and Setter´s



}
