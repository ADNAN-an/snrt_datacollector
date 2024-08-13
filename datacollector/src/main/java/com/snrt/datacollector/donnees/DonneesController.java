package com.snrt.datacollector.donnees;

import com.snrt.datacollector.plateforme.Plateforme;
import com.snrt.datacollector.plateforme.PlateformeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DonneesController {

    private final DonneesService donneesService;
    private final PlateformeRepository plateformeRepository;

    @Autowired
    public DonneesController(DonneesService donneesService, PlateformeRepository plateformeRepository) {
        this.donneesService = donneesService;
        this.plateformeRepository = plateformeRepository;
    }

    @GetMapping
    public List<Donnees> getAll() {
        return donneesService.getData();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addData(@RequestBody DonneesRequestDto data) {
        try {
            Donnees newData = new Donnees();
            newData.setEventType(data.getEventType());
            newData.setValue(data.getValue());
            newData.setIpAddress(data.getIpAddress());
            newData.setCountry(data.getCountry());
            newData.setCity(data.getCity());
            newData.setBrowser(data.getBrowser().getName() + " " + data.getBrowser().getVersion());
            newData.setDeviceType(data.getDeviceType());
            newData.setOperatingSystem(data.getOperatingSystem().getName() + " " + data.getOperatingSystem().getVersion());
            newData.setCreationDate(new Date());

            Plateforme plateforme = plateformeRepository.findByPlatformName(data.getPlateformeName())
                    .orElseGet(() -> {
                        Plateforme newPlateforme = new Plateforme();
                        newPlateforme.setPlatformName(data.getPlateformeName());
                        return plateformeRepository.save(newPlateforme);
                    });
            newData.setPlateforme(plateforme);

            donneesService.addData(newData);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }



    @DeleteMapping("/delete/{dataId}")
    public ResponseEntity<String> deleteData(@PathVariable("dataId") int dataId) {
        try {
            donneesService.deleteData(dataId);
            return ResponseEntity.ok("Data deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
