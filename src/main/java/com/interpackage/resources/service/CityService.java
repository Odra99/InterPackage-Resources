package com.interpackage.resources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(
            new Response(this.cityRepository.findAll()), HttpStatus.OK);
    }

    
}
