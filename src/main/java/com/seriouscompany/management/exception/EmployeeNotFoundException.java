package com.seriouscompany.management.exception;

public class EmployeeNotFoundException extends ResourceNotFoundException {

    private static final String RESOURCE_NAME = "Employee";

    public EmployeeNotFoundException(Long id) {
        super(RESOURCE_NAME, id);
    }
    
}
