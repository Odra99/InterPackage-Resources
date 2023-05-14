package com.interpackage.resources.service;

import com.interpackage.resources.interfaces.RouteInterface;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.repository.RouteRepository;
import com.interpackage.resources.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * @return Un objeto ResponseEntity<Response> que contiene la
     * respuesta HTTP. Si la ruta se crea correctamente,
     * la respuesta contendrá el código de estado HTTP 201 (CREATED)
     * y un objeto Response que contiene
     * la información de la ruta creada. Si la ruta ya existe,
     * la respuesta contendrá el código de estado HTTP 400
     * (BAD REQUEST) y un objeto Response que contiene un mensaje de error.
     * Si algún campo requerido falta o es nulo,
     * la  respuesta contendrá el código de estado HTTP 400 (BAD REQUEST)
     * y un objeto Response que contiene
     * un mensaje de error.
     */
    public ResponseEntity<Response> create(Route route) {
        try {
            if (this.routeRepository.existsRouteByName(route.getName())){
                return ResponseEntity
                        .badRequest()
                        .body(new Response("Ya existe una ruta con el nombre: " + route.getName()));
            } else {
                return new ResponseEntity<>(new Response(this.routeRepository.save(route)), HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(Constants.REQUIRED_FIELDS));
        }
    }


    /**
     * Edita una ruta existente con la información proporcionada en el objeto Route.
     * Si ya existe una ruta con el mismo nombre y diferente ID, devuelve un error.
     * Si falta algún campo requerido, devuelve un error.
     * Si se edita exitosamente la ruta, devuelve una respuesta con el objeto Route actualizado.
     * @param route objeto Route con la información actualizada de la ruta.
     * @return una respuesta ResponseEntity que incluye un objeto Response con
     *         información sobre el éxito o fallo de la edición.
     */
    public ResponseEntity<Response> edit(Route route) {
        try {
            if (this.routeRepository
                    .existsRouteByNameAndRouteIdIsNot(
                            route.getName(), route.getRouteId())){
                return ResponseEntity
                        .badRequest()
                        .body(new Response("Ya existe una ruta con el nombre: " + route.getName()));
            } else {
                Optional<Route> updated = this.routeRepository.findById(route.getRouteId());
                if (updated.isPresent()) {
                    Route updatedRoute = updated.get();
                    updatedRoute.setDestination(route.getDestination());
                    updatedRoute.setName(route.getName());
                    updatedRoute.setOrigin(route.getOrigin());
                    updatedRoute.setPriceWeight(route.getPriceWeight());
                    return new ResponseEntity<>(
                            new Response(this.routeRepository.save(updatedRoute)), HttpStatus.OK);
                }
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(Constants.REQUIRED_FIELDS));
        }
    }

    /**
     * Busca una ruta por su ID y devuelve una respuesta HTTP
     * que contiene la ruta si se encuentra,
     * o un error 404 si no se encuentra. Maneja la excepción
     * DataIntegrityViolationException si se produce
     * un error de integridad de datos.
     * @param id el ID de la ruta a buscar.
     * @return una respuesta HTTP que contiene la ruta
     * si se encuentra, o un error 404 si no se encuentra.
     */
    public ResponseEntity<Response> getById(Long id) {
        try {
            Optional<Route> route = this.routeRepository.findById(id);
            return route.map(
                    value -> new ResponseEntity<>(
                            new Response(value), HttpStatus.OK))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(Constants.REQUIRED_FIELDS));
        }
    }

    /**
     * Obtiene todas las rutas registradas en el sistema.
     * @return ResponseEntity con la lista de rutas y el HttpStatus correspondiente.
     */
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(
                new Response(routeRepository.findAll()), HttpStatus.OK);
    }


    /**
     * Deletes a route by id.
     * @param id the id of the route to delete
     * @return a response entity indicating the result of the operation
     */
    public ResponseEntity<Response> delete(Long id) {
        try{
            Optional<Route> optionalRoute = routeRepository.findById(id);
            if (optionalRoute.isPresent()) {
                Route route = optionalRoute.get();
                route.setDeleted(Boolean.TRUE);
                return new ResponseEntity<>(
                        new Response(
                                this.routeRepository.save(route)), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(new Response(Constants.REQUIRED_FIELDS));
        }
    }
}
