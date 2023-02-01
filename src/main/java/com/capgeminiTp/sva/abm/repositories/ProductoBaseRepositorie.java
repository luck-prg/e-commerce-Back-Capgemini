package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.producto.ProductoBase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("jpa")
public interface ProductoBaseRepositorie extends JpaRepository<ProductoBase,Long>{
}
