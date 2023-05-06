package com.interpackage.resources.model;

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
@Table (name = "path")
public class Path {
  
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "path_id", nullable = false)
    private Long pathId;

    @Column (name = "name", nullable = false, length = 75)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "route_id")
    private Route route;


    @Column (name = "origin", nullable = false)
    private Long origin;

    @Column (name = "destination", nullable = false)
    private Long destination;

    @Column (name = "priority", nullable = false)
    private int priority;

    
}
