/**
 * Este archivo es para controlar las rutas
 */
package com.interpackage.resources.controller;

import com.interpackage.resources.aspect.RequiredRole;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.service.RouteService;
import com.interpackage.resources.util.Constants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las rutas.
 */
@RestController
@RequestMapping(Constants.API_RESOURCES_V1 + "/route")
public class RouteController {

    // Servicio de rutas
    private final RouteService routeService;

    /**
     * Constructor de la clase RouteController que inicializa la dependencia RouteService
     * mediante inyección de dependencias, almacenándola en una variable de instancia final.
     *
     * @param routeService Servicio de rutas que se utilizará en este controlador.
     */
    public RouteController(final RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Maneja una petición HTTP POST a la raíz de la aplicación para agregar una nueva ruta.
     *
     * @param route Objeto de tipo Route que contiene la información de la nueva ruta.
     * @return Objeto ResponseEntity<Response> que contiene la respuesta HTTP.
     *         Si se crea correctamente la ruta,
     *         la respuesta contendrá el código de estado HTTP 201 (CREATED) y un objeto
     *         Response que contiene la información
     *         de la ruta creada. Si ocurre un error durante la creación de la ruta,
     *         la respuesta contendrá
     *         el código de estado HTTP 500 (INTERNAL SERVER ERROR) y un cuerpo vacío.
     */
    @PostMapping("/")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> addRoute(final @RequestBody Route route) {
        try {
            return routeService.create(route);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * Maneja una petición HTTP PUT a la raíz de la aplicación para editar una ruta.
     *
     * @param route Objeto de tipo Route que contiene la información de la nueva ruta.
     * @return Objeto ResponseEntity<Response> que contiene la respuesta HTTP.
     *         Si se edita correctamente la ruta,
     *         la respuesta contendrá el código de estado HTTP 200 (OK) y un objeto
     *         Response que contiene la información de la ruta editada. Si
     *         ocurre un error durante la edición de la ruta, la respuesta contendrá
     *         el código de estado HTTP 500 (INTERNAL SERVER ERROR) y un cuerpo vacío.
     */
    @PutMapping("/")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> editRoute(final @RequestBody Route route) {
        try {
            return routeService.edit(route);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * Obtiene una ruta por su identificador.
     * @param id El identificador de la ruta a obtener.
     * @return ResponseEntity con el objeto Response y código
     *         HTTP 200 si la operación fue exitosa.
     *         ResponseEntity con un objeto Response de error y
     *         código HTTP 500 si hubo un error en el servidor.
     */
    @GetMapping(value = "/{id}")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> getRoute(final @PathVariable Long id) {
        try {
            return routeService.getById(id);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * Método que retorna todas las rutas existentes en la base de datos.
     * @return ResponseEntity con el resultado de la consulta a través del servicio de rutas.
     */
    @GetMapping("/")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> getRoutes() {
        return routeService.getAll();
    }

    /**
     * Delete a Route entity by its ID.
     * @param id the ID of the Route entity to delete
     * @return a ResponseEntity with a Response entity and HttpStatus.
     * OK if the entity was successfully deleted, or a ResponseEntity
     * with nobody and HttpStatus.NOT_FOUND if the entity was not found,
     * or a ResponseEntity with nobody and HttpStatus.
     * INTERNAL_SERVER_ERROR if an error occurred while deleting the entity
     */
    @DeleteMapping(value = "/{id}")
    @RequiredRole({Constants.ADMIN_ROL})
    public ResponseEntity<Response> deleteRoute(final @PathVariable Long id){
        try {
            return routeService.delete(id);
        } catch (final Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
