package com.interpackage.resources.interfaces;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Path;
import org.springframework.http.ResponseEntity;

public interface PathInterface {

    ResponseEntity<Response> create(Path route);
    ResponseEntity<Response> getById(Long id);
    ResponseEntity<Response> update(Path path);
    ResponseEntity<Response> delete(Long id);
    ResponseEntity<Response> changeStatus(Long id);
}
