package com.snrt.datacollector.controllers;

import com.snrt.datacollector.DTO.DonneesRequestDto;
import com.snrt.datacollector.services.DonneesService;
import com.snrt.datacollector.models.Donnees;
import com.snrt.datacollector.models.Plateforme;
import com.snrt.datacollector.repositories.PlateformeRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> addData(@RequestBody DonneesRequestDto data, HttpServletRequest request) {
        try {
            //GET IP ADDRESS
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }

            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null || userAgent.isEmpty()) {
                return ResponseEntity.badRequest().body("User-Agent header is missing.");
            }

            //GET DEVICE TYPE
            String deviceType = donneesService.detectDeviceType(userAgent);

            //GET COUNTRY
            Map<String, String> locationInfo = donneesService.ipInfo(ipAddress, "location");

            // Get Browser Name and Version
            String browserName = donneesService.getBrowserName(userAgent);
            String browserVersion = donneesService.getBrowserVersion(userAgent);

            //Get OS Name
            String osName = donneesService.getOperatingSystemName(userAgent);

            Donnees newData = new Donnees();
            newData.setEventType(data.getEventType());
            newData.setValue(data.getValue());
            newData.setIpAddress(ipAddress);
            newData.setCountry(locationInfo.getOrDefault("country", "Unknown"));
            newData.setCity(locationInfo.getOrDefault("city", "Unknown"));
            newData.setBrowser(browserName + " " + browserVersion);
            newData.setDeviceType(deviceType);
            newData.setOperatingSystem(osName);
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
