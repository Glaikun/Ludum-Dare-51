package com.glaikunt.framework.esc.component.statemachine;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.statemachine.StateMachine;

public class StateComponent implements Component {

    private StateMachine stateMachine;
    private boolean pause;

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
