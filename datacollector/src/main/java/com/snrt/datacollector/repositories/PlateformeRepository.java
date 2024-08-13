package com.snrt.datacollector.repositories;

import com.snrt.datacollector.models.Plateforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateformeRepository extends JpaRepository<Plateforme, Integer> {
    Optional<Plateforme> findByPlatformName(String platformName);
}
