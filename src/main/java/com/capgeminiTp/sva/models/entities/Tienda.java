package com.capgeminiTp.sva.models.entities;

import com.capgeminiTp.sva.models.entities.carrito.Carrito;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tiendas")
@Getter @Setter
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "vendedor_id",referencedColumnName = "id")
    private Usuario vendedor;

    @JsonIgnore
    @OneToMany(mappedBy = "tiendaVendedor", cascade = {CascadeType.ALL})
    private List<Carrito> carritosAsociados;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<MetodoDePago> metodospago;

    public Tienda(){
        this.carritosAsociados = new ArrayList<Carrito>();
        this.metodospago = new ArrayList<MetodoDePago>();
    }

    public Tienda(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public void agregarMetodo(MetodoDePago metodoNuevo){
        this.metodospago.add(metodoNuevo);
    }
}
