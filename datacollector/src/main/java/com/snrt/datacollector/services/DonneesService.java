package com.snrt.datacollector.services;

import com.snrt.datacollector.repositories.DonneesRepository;
import com.snrt.datacollector.models.Donnees;
import com.snrt.datacollector.models.Plateforme;
import com.snrt.datacollector.repositories.PlateformeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DonneesService {

    private final DonneesRepository donneesRepository;
    private final PlateformeRepository plateformeRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public  DonneesService(DonneesRepository donnesRepository , PlateformeRepository plateformeRepository, RestTemplate restTemplate) {
        this.donneesRepository = donnesRepository;
        this.plateformeRepository = plateformeRepository;
        this.restTemplate = restTemplate;
    }

    public List<Donnees> getData() {
        return donneesRepository.findAll();
    }

    public Donnees addData(Donnees donnees){
        return donneesRepository.save(donnees);
    }

    public String detectDeviceType(String agent) {
        if (agent.contains("iPhone")) {
            return "iOS";
        } else if (agent.contains("Android") || agent.contains("Linux")) {
            return "Android";
        } else {
            return "Desktop";
        }
    }

    public Map<String, String> ipInfo(String ip, String purpose) {
        if (ip == null || ip.isEmpty()) {
            return new HashMap<>(); // Return an empty map if no IP is provided
        }

        String apiUrl = "http://www.geoplugin.net/json.gp?ip=" + ip;
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        Map<String, String> result = new HashMap<>();
        if (response != null && response.containsKey("geoplugin_countryCode")) {
            String countryCode = (String) response.get("geoplugin_countryCode");
            String countryName = (String) response.get("geoplugin_countryName");
            String city = (String) response.get("geoplugin_city");

            result.put("country", countryName);
            result.put("city", city);
        }

        return result;
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
