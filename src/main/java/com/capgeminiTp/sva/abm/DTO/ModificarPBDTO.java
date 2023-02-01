package com.capgeminiTp.sva.abm.DTO;

import com.capgeminiTp.sva.models.entities.producto.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModificarPBDTO {

    private String categoria;
    private String imagen;
    private String nombre;
    private String descripcion;
    private int tiempoFabricacion;
    private int precio_Base;
    private Estado estado;
}
