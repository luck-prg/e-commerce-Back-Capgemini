package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.Tienda;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "tiendas")
@Qualifier("jpa")
public interface TiendaRepositorie extends JpaRepository<Tienda,Long> {

    public Tienda findByVendedor(Usuario vendedor);

}
