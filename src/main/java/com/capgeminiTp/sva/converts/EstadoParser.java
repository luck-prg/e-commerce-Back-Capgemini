package com.capgeminiTp.sva.converts;

import com.capgeminiTp.sva.models.entities.producto.Estado;

public class EstadoParser {
    public static String estadoToString(Enum<Estado> estado){
        if (Estado.PAUSADO.equals(estado)) {
            return "PAUSADO";
        } else if (Estado.DESACTIVADO.equals(estado)) {
            return "DESACTIVADO";
        }
        return "PUBLICADO";
    }
    public static Estado stringToEstado(String string){
        switch (string){
            case "PAUSADO": return Estado.PAUSADO;
            case "DESACTIVADO": return Estado.DESACTIVADO;
            default: return Estado.PUBLICADO;
        }
    }
}
