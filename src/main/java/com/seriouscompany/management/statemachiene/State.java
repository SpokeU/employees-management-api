package com.seriouscompany.management.statemachiene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum State {
    ACTIVE,
    APPROVED(ACTIVE),
    IN_CHECK(APPROVED),
    ADDED(IN_CHECK);

    private List<State> validToStates;

    State() {
        this(new ArrayList<>());//TODO unmodifialbe
    }

    State(State toState) {
        this(Arrays.asList(toState));
    }

    State(List<State> toStates) {
        validToStates = toStates;
    }

    public boolean isValidTransition(State toState) {
        return validToStates.contains(toState);
    }
}
