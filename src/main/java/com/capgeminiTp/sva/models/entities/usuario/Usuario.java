package com.capgeminiTp.sva.models.entities.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuario")
@Getter @Setter
public  class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="user", columnDefinition = "VARCHAR(55)")
    private String user;

    @Column(name="password", columnDefinition = "VARCHAR(55)")
    private String password;

    @Column(name = "nombre", columnDefinition = "VARCHAR(55)")
    private String nombre;

    @Column(name = "estado")
//    @Convert(converter = EstadoCuentaParser.class)
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;


    /*
    @Column(name = "estado")
    @Enumerated(value = EnumType.STRING)
    private EstadoCuenta estado; */

    /*
    @Column(name="montoBilletera", columnDefinition = "INTEGER")
    private Integer billetera; */

    /*
    @JsonIgnore
    @OneToMany(mappedBy = "vendedor", cascade = {CascadeType.ALL})
    private List<ProductoBase> productosPublicados; */

    /*
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.ALL})
    private List<Carrito> carritos; */



    public Usuario() {}

    public Usuario(String user, String password, String nombre) {
        this.user = user;
        this.password = password;
        this.nombre = nombre;
    }

    // metodos

    /* public ProductoBase publicar(ProductoBase productoBase){
        return productoBase;
    } */

}
