package com.capgeminiTp.sva.models.entities;

import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "metododepago")
@Getter @Setter
public class MetodoDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "metodo",columnDefinition = "VARCHAR(100)")
    private String metodo;

    @ManyToMany(mappedBy = "metodospago")
    private List<Tienda> tienda;

    public MetodoDePago() {
        this.tienda = new ArrayList<Tienda>();
    }
}

