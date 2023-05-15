package com.interpackage.resources.interfaces;

import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Path;
import org.springframework.http.ResponseEntity;

public interface PathInterface {

    ResponseEntity<Response> create(Path route);
    ResponseEntity<Response> getById(Long id);
}
