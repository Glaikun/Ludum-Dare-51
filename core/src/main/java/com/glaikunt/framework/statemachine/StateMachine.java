package com.glaikunt.framework.statemachine;

public class StateMachine {

    private State initState;
    private State currentState;

    public StateMachine(State currentState) {
        this.currentState = currentState;
        this.initState = currentState;
    }

    public void act(float delta) {

        Transition triggeredTransition = null;

        for (Transition transition : currentState.getTransitions()) {
            if (transition.isTriggered()) {
                triggeredTransition = transition;
                break;
            }
        }

        if (triggeredTransition != null) {
            currentState = triggeredTransition.getTargetState();
            currentState.act(delta);
        } else {
            currentState.act(delta);
        }

    }
}
