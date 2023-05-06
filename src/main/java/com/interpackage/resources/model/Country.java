package com.interpackage.resources.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table (name = "country")
public class Country {
  
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "country_id", nullable = false)
    private Long countryId;

    @Column (name = "name", nullable = false, length = 75)
    private String name;

    @Column (name = "iso_code_1", nullable = false, length = 25)
    private String isoCode1;

    @Column (name = "iso_code_2", nullable = false, length = 25)
    private String isoCode2;

    @Column (name = "iso_code_3", nullable = false, length = 25)
    private String isoCode3;
    
}
