package com.interpackage.resources.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    @NotNull
    private Route route;


    @Column (name = "origin", nullable = false)
    private Long origin;

    @Column (name = "destination", nullable = false)
    private Long destination;

    @Column (name = "priority", nullable = false)
    @NotNull
    private int priority;

    @Column(name="is_deleted",columnDefinition = "boolean DEFAULT 'false'")
    private boolean deleted;

    @Column(name = "is_active",columnDefinition = "boolean default true")
    private boolean active = true;
    
    public void merge(Path path){
        name = path.name;
        route = path.route;
        origin = path.origin;
        destination = path.destination;
        priority = path.priority;
    }
}
