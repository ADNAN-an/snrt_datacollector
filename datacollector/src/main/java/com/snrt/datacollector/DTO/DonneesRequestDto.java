package com.snrt.datacollector.DTO;

import lombok.Data;

@Data
public class DonneesRequestDto {
    private String eventType;
    private String page_uri;
    private String ipAddress;
    private String country;
    private String city;
    private BrowserDto browser;
    private String deviceType;
    private OperatingSystemDto operatingSystem;
    private String plateformeName;
    private String additionalInfo;
    @Data
    public static class BrowserDto {
        private String name;
        private String version;
    }

    @Data
    public static class OperatingSystemDto {
        private String name;
        private String version;
    }
}
