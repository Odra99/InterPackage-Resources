package com.interpackage.resources.service;

import com.interpackage.resources.interfaces.RouteInterface;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RouteService implements RouteInterface {

    private final RouteRepository routeRepository;

    @Autowired
    public  RouteService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    /**
     * Crea una nueva ruta.
     *
     * @param route El objeto de tipo Route que contiene la información de la nueva ruta.
     * @return Un objeto ResponseEntity<Response> que contiene la respuesta HTTP. Si la ruta se crea correctamente,
     *         la respuesta contendrá el código de estado HTTP 201 (CREATED) y un objeto Response que contiene
     *         la información de la ruta creada. Si la ruta ya existe, la respuesta contendrá el código de estado HTTP 400
     *         (BAD REQUEST) y un objeto Response que contiene un mensaje de error. Si algún campo requerido falta o es nulo,
     *         la respuesta contendrá el código de estado HTTP 400 (BAD REQUEST) y un objeto Response que contiene
     *         un mensaje de error.
     */
    public ResponseEntity<Response> create(Route route){
        try {
            if (this.routeRepository.existsRouteByName(route.getName())){
                return ResponseEntity
                        .badRequest()
                        .body(new Response("Ya existe una ruta con el nombre: " + route.getName()));
            } else {
                return new ResponseEntity<Response>(new Response(this.routeRepository.save(route)), HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Todos los campos son requeridos."));
        }
    }
}
