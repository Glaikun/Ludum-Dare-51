package com.glaikunt.framework.statemachine;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    private final List<Transition> transitions = new ArrayList<>();

    private Entity entity;

    public State(Entity entity) {
        this.entity = entity;
    }

    public abstract void act(float delta);

    public List<Transition> getTransitions() {
        return transitions;
    }

    public Entity getEntity() {
        return entity;
    }
}
