package com.seriouscompany.management.service;

import com.seriouscompany.management.exception.EmployeeNotFoundException;
import com.seriouscompany.management.model.EmployeeEntity;
import com.seriouscompany.management.repository.EmployeeRepository;
import com.seriouscompany.management.statemachiene.Action;
import com.seriouscompany.management.statemachiene.StateProcessor;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final StateProcessor stateProcessor;

    public EmployeeService(EmployeeRepository employeeRepository, StateProcessor stateProcessor) {
        this.employeeRepository = employeeRepository;
        this.stateProcessor = stateProcessor;
    }

    public EmployeeEntity create(EmployeeEntity employee) {
        stateProcessor.process(Action.CREATE, employee);

        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    public EmployeeEntity process(Long id, Action action) {
        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        EmployeeEntity processedEmployee = stateProcessor.process(action, employee);
        return employeeRepository.save(processedEmployee);
    }
}
