package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.capgeminiTp.sva.models.entities.producto.Categoria;
import com.capgeminiTp.sva.models.entities.producto.ProductoPersonalizado;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("jpa")
public interface ProductoPersonalizadoRepositorie extends JpaRepository<ProductoPersonalizado,Long> {
    Page<ProductoPersonalizado>findByCategoria(Categoria categoria, Pageable page);
    Page<ProductoPersonalizado>findByVendedor(Usuario vendedor,Pageable page);

}
