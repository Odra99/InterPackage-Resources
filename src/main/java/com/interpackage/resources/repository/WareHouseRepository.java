package com.interpackage.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interpackage.resources.model.Warehouse;

import java.util.List;

@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsWarehouseByName(String name);
    List<Warehouse> findAllByDeleted(boolean deleted);
}
