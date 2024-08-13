package com.snrt.datacollector.plateforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlateformeService {
    private final PlateformeRepository plateformeRepository;

    @Autowired
    public PlateformeService(PlateformeRepository plateformeRepository) {
        this.plateformeRepository = plateformeRepository;
    }
}
