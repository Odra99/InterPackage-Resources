package com.interpackage.resources.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.interpackage.resources.interfaces.PathInterface;
import com.interpackage.resources.model.Path;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.repository.PathRepository;

@Service
public class PathService implements PathInterface {

    @Autowired
    PathRepository pathRepository;

    /**
     * This Java function creates a new path and checks if a
     * path with the same name already
     * exists.
     * 
     * @param path The parameter "path" is an object
     *             of the class path.
     * @return This method returns a ResponseEntity object that
     *         contains a Response object and an HTTP
     *         status code.
     */
    @Override
    public ResponseEntity<Response> create(Path path) {

        if (!this.pathRepository.existsPathByName(path.getName())) {
            return new ResponseEntity<>(new Response(this.pathRepository.save(path)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Response("Ya existe path con el nombre " + path.getName()),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<Response> getById(Long id) {
        try {
            var path = this.pathRepository.findById(id).orElse(null);
            if (path!=null) {
               return new ResponseEntity<>(new Response(path), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Response(),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(),
                    HttpStatus.NOT_FOUND);
        }
    }

}
