package com.interpackage.resources.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "city")
public class City {
  
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "city_id", nullable = false)
    private Long cityId;

    @Column (name = "name", nullable = false, length = 75)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    private Country country;
    
    
}
