package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.carrito.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "items")
@Qualifier("jpa")
public interface ItemrRepositorie extends JpaRepository<Item,Long> {
}
