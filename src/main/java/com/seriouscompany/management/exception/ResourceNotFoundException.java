package com.seriouscompany.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        this(String.format("'%s' with id: '%d' is not found", resourceName, id));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
