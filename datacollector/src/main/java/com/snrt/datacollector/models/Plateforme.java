package com.snrt.datacollector.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Plateforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int platformId;
    @Column(unique = true)
    private String platformName;
}
