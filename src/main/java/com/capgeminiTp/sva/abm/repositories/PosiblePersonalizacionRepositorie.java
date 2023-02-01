package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.producto.PosiblePersonalizacion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("jpa")
public interface PosiblePersonalizacionRepositorie extends JpaRepository<PosiblePersonalizacion,Long> {
}
