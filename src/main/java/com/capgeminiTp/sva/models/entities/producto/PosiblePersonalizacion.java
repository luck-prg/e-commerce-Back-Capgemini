package com.capgeminiTp.sva.models.entities.producto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posiblePersonalizacion")
@Getter @Setter
public class PosiblePersonalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Embedded
    private AreaPersonalizacion area;

    @ManyToOne
    @JoinColumn(name = "productoB_id", referencedColumnName = "id")
    private ProductoBase producto;

    // @JsonIgnore
    @Embedded
    private TipoPersonalizacion tipo;


    // Constructor
    
    public PosiblePersonalizacion(){}


}
