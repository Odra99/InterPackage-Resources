package com.interpackage.resources.service;

import com.interpackage.resources.util.Constants;
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
        }catch(jakarta.validation.ConstraintViolationException ignored){
            return ResponseEntity
                    .badRequest()
                    .body(new Response(Constants.REQUIRED_FIELDS));
        }catch (Exception exception) {
            return ResponseEntity
                    .internalServerError()
                    .body(new Response(Constants.INTERNAL_SERVER_ERROR_DB));
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
                return ResponseEntity.badRequest().body(new Response(Constants.REQUIRED_ID));
            }
            var newWareHouse = this.wareHouseRepository.save(warehouse);
            return new ResponseEntity<>(new Response(newWareHouse), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(new Response(Constants.INTERNAL_SERVER_ERROR_DB));
        }
    }

    /**
     * @param warehouse
     * @return
     */
    @Override
    public ResponseEntity<Response> delete(Long pk) {
        try{
            if(pk == null){
                return ResponseEntity.badRequest().body(new Response(Constants.REQUIRED_ID));
            }
            var warehouse_optional = wareHouseRepository.findById(pk);
            if (warehouse_optional.isEmpty()){
                return new ResponseEntity<>(new Response(), HttpStatus.NOT_FOUND);
            }
            var warehouse = warehouse_optional.get();
            warehouse.setDeleted(true);
            var newWareHouse = this.wareHouseRepository.save(warehouse);
            return new ResponseEntity<>(new Response(newWareHouse), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(new Response(Constants.INTERNAL_SERVER_ERROR_DB));
        }
    }


    /**
     * This Java function returns a ResponseEntity object containing a Response object with data
     * retrieved from a warehouse repository based on a given primary key, or an error message if an
     * exception occurs.
     * 
     * @param pk pk is a variable of type Long that represents the primary key of a record in a
     * database table.
     * @return A ResponseEntity object is being returned. It contains a Response object and an HTTP
     * status code.
     */
    @Override
    public ResponseEntity<Response> findById(Long pk) {
        try{
            return new ResponseEntity<>(new Response(wareHouseRepository.findById(pk).orElseGet(null)), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(new Response(Constants.INTERNAL_SERVER_ERROR_DB));
        }
    }


    /**
     * This Java function returns all items in a warehouse repository as a response entity, with error
     * handling.
     * 
     * @return This method returns a ResponseEntity object that contains a Response object with a list
     * of all the items in the warehouse. if a error is encountered return a internal server error status
     */
    @Override
    public ResponseEntity<Response> findAll() {
        try{
            return new ResponseEntity<>(new Response(wareHouseRepository.findAllByDeleted(false)), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(new Response("Error al operar petición"+e.getMessage()));
        }
    }

}
