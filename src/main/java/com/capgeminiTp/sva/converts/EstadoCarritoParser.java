package com.capgeminiTp.sva.converts;

import com.capgeminiTp.sva.models.entities.carrito.EstadoCarrito;

public class EstadoCarritoParser {
    public static String estadoToString(Enum<EstadoCarrito> estado){
        if (EstadoCarrito.ABIERTO.equals(estado)) {
            return "ABIERTO";
        }
        return "FINALIZADO";
    }
    public static EstadoCarrito stringToEstado(String string){
        switch (string){
            case "ABIERTO": return EstadoCarrito.ABIERTO;
            default: return EstadoCarrito.FINALIZADO;
        }
    }
}
