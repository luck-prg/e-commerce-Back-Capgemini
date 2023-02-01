package com.capgeminiTp.sva.abm.DTO;

import com.capgeminiTp.sva.models.entities.carrito.Carrito;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOagregarItem {
    private Carrito carrito;
    private Long comprador_id;
    private int cantidad;
    private Long producto_id;
}
