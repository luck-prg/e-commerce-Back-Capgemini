package com.capgeminiTp.sva.abm.DTO;

import com.capgeminiTp.sva.models.entities.usuario.TipoUsuario;
import lombok.*;

@Getter @Setter
public class DTOSession {
    private String name;
    private String user;
    private String password;
    private Long user_id;
    private TipoUsuario tipo;
    private String opCode;
}
