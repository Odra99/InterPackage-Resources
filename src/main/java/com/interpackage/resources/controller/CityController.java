package com.interpackage.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.interpackage.resources.aspect.RequiredRole;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.service.CityService;
import com.interpackage.resources.util.Constants;

@Controller
@RequestMapping(Constants.API_RESOURCES_V1 + "/cities")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping("/")
    @RequiredRole({"Admin","Receptionist"})
    public ResponseEntity<Response> getPaths() {
        return cityService.getAll();
    }
    
}

