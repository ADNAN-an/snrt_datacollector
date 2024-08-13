package com.snrt.datacollector.controllers;

import com.snrt.datacollector.services.PlateformeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plateforme")
public class PlateformeController {
    private PlateformeService plateformeService;

    @Autowired
    public PlateformeController(PlateformeService plateformeService) {
        this.plateformeService = plateformeService;
    }

}
