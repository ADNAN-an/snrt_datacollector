package com.snrt.datacollector.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Donnees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dataId;
    private String ipAddress;
    private String eventType;
    private String value;
    private String country;
    private String city;
    private String browser;
    private String deviceType;
    private String operatingSystem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "plateforme_id")
    private Plateforme plateforme;
}
