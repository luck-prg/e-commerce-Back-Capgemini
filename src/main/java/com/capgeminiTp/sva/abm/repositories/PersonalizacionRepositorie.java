package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.producto.Personalizacion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("jpa")
public interface PersonalizacionRepositorie extends JpaRepository<Personalizacion,Long> {
}
