package com.interpackage.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.interpackage.resources.model.Path;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.service.PathService;
import com.interpackage.resources.util.Constants;

import jakarta.validation.Valid;

@Controller
@RequestMapping(Constants.API_RESOURCES_V1 + "/paths")
public class PathController {

    @Autowired
    PathService pathService;

    /**
     * This is a Java function that creates a path
     * and returns a response entity
     * 
     * @param path The parameter "path" it is
     *             expected to be a JSON object in the request body.
     * @return The method is returning a ResponseEntity
     *         object that contains a Response object.
     */
    @PostMapping("/")
    public ResponseEntity<Response> createPath(@Valid @RequestBody Path path) {
        try {
            return pathService.create(path);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Response> getPath(@PathVariable Long id) {
        return pathService.getById(id);
    }

    @PutMapping("/")
    public ResponseEntity<Response> updatePath(@Valid @RequestBody Path path) {
        try {
            return pathService.update(path);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return pathService.delete(id);
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<Response> setStatus(@PathVariable Long id) {
        try {
            return pathService.changeStatus(id);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
