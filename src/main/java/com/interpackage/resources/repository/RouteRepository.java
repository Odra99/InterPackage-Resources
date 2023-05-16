package com.interpackage.resources.repository;

import com.interpackage.resources.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    boolean existsRouteByName(String name);
    boolean existsRouteByNameAndRouteIdIsNot(String name, Long id);
}
