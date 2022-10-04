package com.glaikunt.framework.statemachine;

import com.badlogic.ashley.core.Entity;

public abstract class Transition {

    private final Entity entity;

    public Transition(Entity entity) {
        this.entity = entity;
    }

    public abstract boolean isTriggered();
    public abstract State getTargetState();

    public Entity getEntity() {
        return entity;
    }
}
