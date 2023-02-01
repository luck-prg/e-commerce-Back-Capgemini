package com.capgeminiTp.sva.abm.repositories;

import com.capgeminiTp.sva.models.entities.MetodoDePago;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("jpa")
public interface MetodoPagoRepositorie extends JpaRepository<MetodoDePago, Long> {

    public Optional<MetodoDePago> findByMetodo(String metodo);
}
