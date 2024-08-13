package com.snrt.datacollector.services;

import com.snrt.datacollector.repositories.PlateformeRepository;
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
