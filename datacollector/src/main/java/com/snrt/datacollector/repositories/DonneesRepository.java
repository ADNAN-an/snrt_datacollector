package com.snrt.datacollector.repositories;

import com.snrt.datacollector.models.Donnees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonneesRepository extends JpaRepository<Donnees, Integer> {
}
