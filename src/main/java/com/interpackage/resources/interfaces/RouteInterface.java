package com.interpackage.resources.interfaces;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import org.springframework.http.ResponseEntity;

public interface RouteInterface {

    ResponseEntity<Response> create(Route route);
    ResponseEntity<Response> edit(Route route);
    ResponseEntity<Response> getById(Long id);
    ResponseEntity<Response> getAll();
    ResponseEntity<Response> delete(Long id);
}
