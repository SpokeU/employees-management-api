package com.seriouscompany.management.exception;

import com.seriouscompany.management.statemachiene.StateProcessor;
import com.seriouscompany.management.statemachiene.exception.IllegalAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(StateProcessor.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFound(ResourceNotFoundException e) {
        logger.error("Resource not found:", e);
        return new ApiError(String.valueOf(HttpStatus.NOT_FOUND.value()), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationError(MethodArgumentNotValidException e) {
        logger.error("Validation error:", e);
        return new ApiError(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }

    @ExceptionHandler(IllegalAction.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleStateMachineError(IllegalAction e) {
        logger.error("State machine error:", e);
        return new ApiError(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception e) {
        logger.error("Api error:", e);
        return new ApiError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
    }

}
