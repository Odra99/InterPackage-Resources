package com.interpackage.resources.controller;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.service.RouteService;
import com.interpackage.resources.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_RESOURCES_V1 + "/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Maneja una petición HTTP POST a la raíz de la aplicación para agregar una nueva ruta.
     *
     * @param route El objeto de tipo Route que contiene la información de la nueva ruta.
     * @return Un objeto ResponseEntity<Response> que contiene la respuesta HTTP. Si se crea correctamente la ruta,
     *         la respuesta contendrá el código de estado HTTP 201 (CREATED) y un objeto Response que contiene la información
     *         de la ruta creada. Si ocurre un error durante la creación de la ruta, la respuesta contendrá
     *         el código de estado HTTP 500 (INTERNAL SERVER ERROR) y un cuerpo vacío.
     */
    @PostMapping("/")
    public ResponseEntity<Response> addRoute(@RequestBody Route route) {
        try {
            return routeService.create(route);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
