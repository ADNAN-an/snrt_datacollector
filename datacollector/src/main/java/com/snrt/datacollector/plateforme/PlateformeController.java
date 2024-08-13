package com.snrt.datacollector.plateforme;

import com.snrt.datacollector.donnees.Donnees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
