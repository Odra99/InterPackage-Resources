package com.interpackage.resources.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "route")
public class Route {
  
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "route_id", nullable = false)
    private Long routeId;

    @Column (name = "name", nullable = false, length = 75)
    private String name;

    @Column(name = "price_weight", precision = 20, scale = 6, nullable = false)
    private BigDecimal priceWeight;

    @Column (name = "origin", nullable = false)
    private Long origin;

    @Column (name = "destination", nullable = false)
    private Long destination;
}
