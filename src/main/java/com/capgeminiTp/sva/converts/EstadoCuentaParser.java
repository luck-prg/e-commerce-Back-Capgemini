package com.capgeminiTp.sva.converts;

import com.capgeminiTp.sva.models.entities.usuario.EstadoCuenta;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstadoCuentaParser implements AttributeConverter<EstadoCuenta,String> {

    @Override
    public String convertToDatabaseColumn(EstadoCuenta estadoCuenta) {
        String estado = null;
        switch (estadoCuenta){
            case ACTIVADA: estado = "ACTIVADA" ;
                break;
            case DESCATIVADA: estado = "DESACTIVADA";
                break;
        }
        return null;
    }

    @Override
    public EstadoCuenta convertToEntityAttribute(String s) {
        if( s == null ){
            return null;
        }
        EstadoCuenta estadoCuenta;
        switch (s){
            case "ACTIVADA": estadoCuenta = EstadoCuenta.ACTIVADA;
                break;
            case "DESACTIVADA": estadoCuenta = EstadoCuenta.DESCATIVADA;
            default:
                try {
                    throw new IllegalAccessException(s + " n o es un estado");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
        }
        return null;
    }
}
