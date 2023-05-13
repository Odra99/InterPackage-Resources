package com.interpackage.resources.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table (name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column (name = "name", nullable = false, length = 75)
    @NotBlank
    @NotEmpty
    private String name;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    @NotNull
    private City city;

    @Column (name = "warehouse_type", nullable = false)
    @NotNull
    private Long warehouseType;

    @Column (name = "address_line", nullable = false, length = 500)
    @NotBlank
    @NotEmpty
    private String addressLine;

}
