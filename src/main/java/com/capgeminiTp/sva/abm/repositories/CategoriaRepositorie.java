package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.producto.Categoria;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("jpa")
public interface CategoriaRepositorie extends JpaRepository<Categoria,Long> {
    public Optional<Categoria> findByCategoria(String categoria);
}
