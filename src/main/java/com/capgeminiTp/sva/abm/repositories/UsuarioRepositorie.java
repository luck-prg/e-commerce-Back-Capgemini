package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.usuario.TipoUsuario;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("jpa")
public interface UsuarioRepositorie extends JpaRepository<Usuario,Long>{

    public Optional<Usuario> findByUser(String user);
    Page<Usuario> findByTipo(TipoUsuario tipo, Pageable page);
    Page<Usuario> findByTipoAndNombre(TipoUsuario tipo,String nombre, Pageable page);

}
