package com.capgeminiTp.sva.models.entities.producto;

import javax.persistence.*;

@Embeddable
public class TipoPersonalizacion {

    @Column(name = "tipo",columnDefinition = "VARCHAR(100)")
    private String tipo; // TEXTO-FUENTE-COLOR

    // Constructor

    public TipoPersonalizacion(){}

    public TipoPersonalizacion(String tipo) {
        this.tipo = tipo;
    }

    // Getter´s and Setter´s

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
