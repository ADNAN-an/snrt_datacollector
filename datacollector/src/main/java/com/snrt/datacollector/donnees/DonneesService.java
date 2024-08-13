package com.snrt.datacollector.donnees;

import com.snrt.datacollector.plateforme.Plateforme;
import com.snrt.datacollector.plateforme.PlateformeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DonneesService {

    private final DonneesRepository donneesRepository;
    private final PlateformeRepository plateformeRepository;


    @Autowired
    public  DonneesService(DonneesRepository donnesRepository , PlateformeRepository plateformeRepository) {
        this.donneesRepository = donnesRepository;
        this.plateformeRepository = plateformeRepository;
    }

    public List<Donnees> getData() {
        return donneesRepository.findAll();
    }

    public Donnees addData(Donnees donnees){
        return donneesRepository.save(donnees);
    }

    public Plateforme addPlateforme(Plateforme plateforme) {
        return plateformeRepository.save(plateforme);
    }

    public void deleteData(int dataId) {
        boolean exists = donneesRepository.existsById(dataId);

        if (!exists) {
            throw new RuntimeException("Data not found with ID: " + dataId);
        } else {
            donneesRepository.deleteById(dataId);
        }
    }
}
