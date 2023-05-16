package com.interpackage.resources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.interpackage.resources.interfaces.PathInterface;
import com.interpackage.resources.model.Path;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.repository.PathRepository;
import com.interpackage.resources.repository.RouteRepository;

@Service
public class PathService implements PathInterface {

    @Autowired
    PathRepository pathRepository;

    @Autowired
    RouteRepository routeRepository;

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
            var pathDB = this.pathRepository.findById(id).orElse(null);
            if (pathDB == null) {
                return new ResponseEntity<>(new Response(),
                HttpStatus.NOT_FOUND);
            }
            if (pathDB.isDeleted()) {
                return new ResponseEntity<>(new Response("Trayecto no encontrado"),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new Response(pathDB), HttpStatus.OK);
           
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Response> update(Path path) {
        if (this.pathRepository.existsPathByNameAndPathIdIsNot(path.getName(),path.getPathId())) {
            return new ResponseEntity<>(new Response("Ya existe path con el nombre " + path.getName()),
                    HttpStatus.BAD_REQUEST);
        }

        var route = this.routeRepository.findById(path.getRoute().getRouteId()).orElse(null);

        if (route == null) {
            return new ResponseEntity<>(new Response("Ruta no encontrada"),
                    HttpStatus.BAD_REQUEST);
        }
        var pathDB = this.pathRepository.findById(path.getPathId()).orElse(null);

        if (pathDB == null) {
            return new ResponseEntity<>(new Response("Trayecto no encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        if (pathDB.isDeleted()) {
            return new ResponseEntity<>(new Response("Trayecto no encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        pathDB.merge(path);

        return new ResponseEntity<>(new Response(this.pathRepository.save(pathDB)), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> delete(Long id){
        var pathDB = this.pathRepository.findById(id).orElse(null);

        if (pathDB == null) {
            return new ResponseEntity<>(new Response("Trayecto no encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        if (pathDB.isDeleted()) {
            return new ResponseEntity<>(new Response("Trayecto no encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        pathDB.setDeleted(true);

        return new ResponseEntity<>(new Response(this.pathRepository.save(pathDB)), HttpStatus.OK);        
    }


}
