package com.snrt.datacollector.plateforme;

import com.snrt.datacollector.donnees.Donnees;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
