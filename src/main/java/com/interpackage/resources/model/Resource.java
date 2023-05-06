package com.interpackage.resources.model;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table (name = "resource")
public class Resource {
  
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "resource_id", nullable = false)
    private Long resourceId;

    @ManyToOne()
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne()
    @JoinColumn(name = "path_id")
    private Path path;

    @Column (name = "priority", nullable = false)
    private int priority;

    @Column (name = "status", nullable = false)
    private boolean status;

    @Column(name = "operating_cost", precision = 23, scale = 20, nullable = false)
    private BigDecimal operatingCost;

    
}
