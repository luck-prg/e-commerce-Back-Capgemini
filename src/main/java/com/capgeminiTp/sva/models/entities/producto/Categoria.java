package com.capgeminiTp.sva.models.entities.producto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
@Getter @Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "categoria",columnDefinition = "VARCHAR(200)")
    private String categoria;

    public Categoria(){}
    public Categoria(String categoria) {
        this.categoria = categoria;
    }
}
