package com.interpackage.resources.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.interpackage.basedomains.aspect.RequiredRole;

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
    @RequiredRole({Constants.ADMIN_ROL})
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
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> updateWarehouse(@Valid @RequestBody Warehouse Warehouse){
        return wareHouseService.update(Warehouse);
    }

    /**
     * This is a Java function that handles a DELETE request to delete a warehouse by its ID and
     * returns a response entity.
     * 
     * @param id The id parameter is a Long type variable that represents the unique identifier of a
     * warehouse that needs to be deleted. It is annotated with @PathVariable to indicate that its
     * value will be extracted from the URL path.
     * @return A ResponseEntity object containing a Response object is being returned.
     */
    @DeleteMapping("/{id}")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> DeleteWarehouse(@PathVariable Long id){
        return wareHouseService.delete(id);
    }

    /**
     * This Java function retrieves a warehouse by its ID and returns a ResponseEntity containing the
     * result.
     * 
     * @param id The "id" parameter is a Long type variable that is used as a path variable in a Spring
     * Boot REST API endpoint. It represents the unique identifier of a warehouse entity that is being
     * requested by a client. The endpoint is mapped to the HTTP GET method using the @GetMapping
     * annotation. The method returns
     * @return A ResponseEntity object containing a Response object is being returned.
     */
    @GetMapping("/{id}")
    @RequiredRole({Constants.USER_ROL})
    public ResponseEntity<Response> GetById(@PathVariable Long id){
        return wareHouseService.findById(id);
    }

    /**
     * This is a Java function that returns all items in a warehouse as a ResponseEntity object.
     * 
     * @return The method `GetAll()` is returning a `ResponseEntity` object that contains a `Response`
     * object. The `Response` object likely contains data related to all the items in the warehouse.
     */
    @GetMapping("/all")
    @RequiredRole({Constants.USER_ROL})
    public ResponseEntity<Response> GetAll(){
        return wareHouseService.findAll();
    }
    
}
