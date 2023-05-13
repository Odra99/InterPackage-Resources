package com.interpackage.resources.interfaces;

import org.springframework.http.ResponseEntity;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Warehouse;

public interface WareHouseInterface {
    
    ResponseEntity<Response> create(Warehouse warehouse);

    ResponseEntity<Response> update(Warehouse warehouse);

    ResponseEntity<Response> delete(Long pk);
}
