package com.capgeminiTp.sva.models.entities.producto;

import javax.persistence.*;


@Embeddable
public class AreaPersonalizacion {

    // Atributos

    @Column(name = "area", columnDefinition = "VARCHAR(100)")
    private String area_personalizacion;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(100)")
    private String descripcion;


    // Constructor

    public AreaPersonalizacion(){}

    public AreaPersonalizacion(String area_personalizacion, String descripcion) {
        this.area_personalizacion = area_personalizacion;
        this.descripcion = descripcion;
    }


    // Getter´s and Setter´s

    public String getArea_personalizacion() {
        return area_personalizacion;
    }

    public void setArea_personalizacion(String area_personalizacion) {
        this.area_personalizacion = area_personalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
