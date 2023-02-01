package com.capgeminiTp.sva.models.entities.producto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "personalizacion")
@Getter @Setter
public class Personalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "posiblePer_id", referencedColumnName = "id")
    private PosiblePersonalizacion posiblePersonalizacion;

    // @Embedded // Tenia pensado hacerlo con lista pero no se puede con Embedded
    @Column(name = "contenido",columnDefinition = "VARCHAR(100)")
    private String contenido;

    @Column(name = "nombre",columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "precio",columnDefinition = "INTEGER")
    private int precio;

    @ManyToOne
    @JoinColumn(name = "productoPersonalizado", referencedColumnName = "id")
    private ProductoPersonalizado productoP;


    // Constructor

    public Personalizacion(){}

    public Personalizacion(PosiblePersonalizacion posiblePersonalizacion, String contenido, String nombre, int precio) {
        this.posiblePersonalizacion = posiblePersonalizacion;
        this.contenido = contenido;
        this.nombre = nombre;
        this.precio = precio;
    }
}
