package com.seriouscompany.management.statemachiene;

public enum Action {

    CREATE(State.ADDED), IN_CHECK(State.IN_CHECK), APPROVE(State.APPROVED), ACTIVATE(State.ACTIVE);

    private State state;

    Action(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
