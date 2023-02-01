package com.capgeminiTp.sva.models.entities.producto;

import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productosPersonalizado")
@Getter @Setter
public class ProductoPersonalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productobase_id", referencedColumnName = "id")
    private ProductoBase producto_Base;

    @Column(name = "estado")
    @Enumerated(value = EnumType.STRING)
    private Estado estado;
    @JsonIgnore
    @OneToMany(mappedBy = "productoP", cascade = {CascadeType.ALL})
    private List<Personalizacion> personalizacion = new ArrayList<Personalizacion>(); // pueden ser varias

    @ManyToOne
    @JoinColumn(name = "userV_id", referencedColumnName = "id")
    private Usuario vendedor;

    @ManyToOne
    @JoinColumn(name = "categoria_id",referencedColumnName = "id")
    private Categoria categoria;

    @Column(name = "nombre",columnDefinition = "VARCHAR(300)")
    private String nombre;

    @Column(name = "precio",columnDefinition = "INTEGER")
    private int precio;

    // private Data fechaPublicacion; // como era lo de la fecha?s


    // Constructor

    public ProductoPersonalizado(){}

    public ProductoPersonalizado(ProductoBase producto_Base, Estado estado, List<Personalizacion> personalizacion, Usuario vendedor) {
        this.producto_Base = producto_Base;
        this.estado = estado;
        this.personalizacion = personalizacion;
        this.vendedor = vendedor;
    }

    public void calcularPrecio(){
        this.precio = this.producto_Base.getPrecio_Base() + this.personalizacion.stream().mapToInt(i -> i.getPrecio()).sum();
    }


}
