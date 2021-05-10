package com.seriouscompany.management.api.controller;

import com.seriouscompany.management.api.model.EmployeeApiModel;
import com.seriouscompany.management.exception.ApiError;
import com.seriouscompany.management.mapper.ApiModelMapper;
import com.seriouscompany.management.model.EmployeeEntity;
import com.seriouscompany.management.service.EmployeeService;
import com.seriouscompany.management.statemachiene.Action;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private ApiModelMapper mapper;

    public EmployeeController(EmployeeService employeeService, ApiModelMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }


    @Operation(summary = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeApiModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeApiModel create(@RequestBody @Valid EmployeeApiModel employee) {
        EmployeeEntity employeeEntity = mapper.toEmployee(employee);
        EmployeeEntity employeeEntityCreated = employeeService.create(employeeEntity);
        EmployeeApiModel employeeApiModel = mapper.toEmployeeApiModel(employeeEntityCreated);
        return employeeApiModel;
    }

    @Operation(summary = "Process employee status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee status successfully processed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeApiModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Validation error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee with provided id not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{id}/{action}")
    public EmployeeApiModel process(@PathVariable Long id, @PathVariable Action action){
        EmployeeEntity employee = employeeService.process(id, action);
        EmployeeApiModel employeeApiModel = mapper.toEmployeeApiModel(employee);
        return employeeApiModel;
    }

}
