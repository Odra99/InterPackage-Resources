package com.interpackage.resources.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Warehouse;
import com.interpackage.resources.service.WareHouseService;
import com.interpackage.resources.util.Constants;

@RestController
@RequestMapping(Constants.API_RESOURCES_V1 + "/warehouse")
public class WareHouseController {

    // Warehouse service
    @Autowired
    private WareHouseService wareHouseService;
    
    /**
     * This is a Java function that creates a warehouse 
     * and returns a response entity
     * @param warehouse The parameter "warehouse" it is 
     * expected to be a JSON object in the request body.
     * @return The method is returning a ResponseEntity
     * object that contains a Response object.
     */
    @PostMapping("/")
    public ResponseEntity<Response> createWareHouse(@Valid @RequestBody Warehouse warehouse) {
        try {
            return wareHouseService.create(warehouse);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * This is a Java function that updates a warehouse using a PUT 
     * request and returns a response entity.
     * @param Warehouse 
     * warehouse entity that needs to be updated in the database. 
     * The @Valid annotation is used to
     * validate the request body against any constraints
     * @return A ResponseEntity.
     */
    @PutMapping("/")
    public ResponseEntity<Response> updateWarehouse(@Valid @RequestBody Warehouse Warehouse){
        return wareHouseService.update(Warehouse);
    }
}
