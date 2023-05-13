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
            return new ResponseEntity<>(new Response(this.wareHouseRepository.save(warehouse)), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Todos los campos son requeridos."));
        }
    }

    
    /**
     * This Java function updates a warehouse and returns a response
     * entity with a success or error
     * message.
     * @param warehouse
     * @return return a response entity with a success or error message
     */
    @Override
    public ResponseEntity<Response> update(Warehouse warehouse) {
        try{
            if(warehouse.getWarehouseId() == null){
                return ResponseEntity.badRequest().body(new Response("Debe ingresar un id"));
            }
            var newWareHouse = this.wareHouseRepository.save(warehouse);
            return new ResponseEntity<>(new Response(newWareHouse), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new Response("Error al actualizar bodega "+e.getMessage()));
        }
    }

}
