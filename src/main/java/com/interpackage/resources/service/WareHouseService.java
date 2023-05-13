package com.interpackage.resources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.interpackage.resources.interfaces.WareHouseInterface;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Warehouse;
import com.interpackage.resources.repository.CityRepository;
import com.interpackage.resources.repository.WareHouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService implements WareHouseInterface{

    /**
     * Create repository to access the database
    */
    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private CityRepository cityRepository;
    
    /**
     * This Java function creates a new warehouse and checks if a 
     * warehouse with the same name already
     * exists.
     * @param warehouse The parameter "warehouse" is an object 
     * of the class Warehouse.
     * @return This method returns a ResponseEntity object that 
     * contains a Response object and an HTTP
     * status code.
     */
    @Override
    public ResponseEntity<Response> create(Warehouse warehouse) {
        try {
            if (this.wareHouseRepository.existsWarehouseByName(warehouse.getName())){
                return ResponseEntity
                        .badRequest()
                        .body(new Response("Ya existe una bodega con el nombre: " + warehouse.getName()));
            } else {
                return new ResponseEntity<>(new Response(this.wareHouseRepository.save(warehouse)), HttpStatus.CREATED);
            } 
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Todos los campos son requeridos."));
        }
    }
    
}
