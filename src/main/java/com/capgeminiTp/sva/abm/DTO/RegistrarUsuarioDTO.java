package com.capgeminiTp.sva.abm.DTO;

import com.capgeminiTp.sva.models.entities.usuario.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrarUsuarioDTO {
    private String nombre;
    private String user;
    private String password;
    private TipoUsuario tipousuario;
}
