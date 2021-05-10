package com.seriouscompany.management.statemachiene;

import com.seriouscompany.management.model.EmployeeEntity;
import com.seriouscompany.management.statemachiene.exception.IllegalAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StateProcessor {

    private Logger logger = LoggerFactory.getLogger(StateProcessor.class);

    /**
     * Processed provided employee with action
     *
     * @param action
     * @param employee
     * @return
     * @throws IllegalAction
     */
    public EmployeeEntity process(Action action, EmployeeEntity employee) {
        State employeeState = employee.getState();

        if ((action == Action.CREATE && employeeState == null) || employeeState.isValidTransition(action.getState())) {
            employee.setState(action.getState());
        } else {
            String message = "Employee with status:" + employee.getState() + " cannot transition to " + action.getState();
            logger.error(message);
            throw new IllegalAction(message);
        }

        return employee;
    }

}
