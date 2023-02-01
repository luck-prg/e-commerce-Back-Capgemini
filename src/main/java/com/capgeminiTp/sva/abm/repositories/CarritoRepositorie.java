package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.Tienda;
import com.capgeminiTp.sva.models.entities.carrito.Carrito;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "carritos")
@Qualifier("jpa")
public interface CarritoRepositorie extends JpaRepository<Carrito,Long> {

    public Page<Carrito>findByComprador(Usuario comprador, Pageable page);
    public Optional<Carrito> findByCompradorAndTiendaVendedor(Usuario comprador, Tienda tienda);

}
