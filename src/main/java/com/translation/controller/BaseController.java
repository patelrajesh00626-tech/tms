package com.translation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class BaseController {

    protected static final String API_VERSION = "/v1";
    
    protected <T> ResponseEntity<T> createdResponse(Long id, T body) {
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();
        return ResponseEntity.created(location).body(body);
    }
    
    protected <T> ResponseEntity<T> okResponse(T body) {
        return ResponseEntity.ok(body);
    }
    
    protected ResponseEntity<Void> noContentResponse() {
        return ResponseEntity.noContent().build();
    }
}
