package com.snrt.datacollector.donnees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonneesRepository extends JpaRepository<Donnees, Integer> {
}
