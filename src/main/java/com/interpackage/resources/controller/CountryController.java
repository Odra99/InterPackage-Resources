package com.interpackage.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.service.CountryService;
import com.interpackage.resources.util.Constants;

@Controller
@RequestMapping(Constants.API_RESOURCES_V1 + "/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/")
    public ResponseEntity<Response> getPaths() {
        return countryService.getAll();
    }
    
}

