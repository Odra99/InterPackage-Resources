package com.interpackage.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interpackage.resources.model.Warehouse;

@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsWarehouseByName(String name);
}
