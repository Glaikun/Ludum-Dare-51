package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent extends Vector2 implements Component {

    private float sprintSpeed;
    private boolean changeDirection;

    public VelocityComponent() {
        super();
    }

    public VelocityComponent(Vector2 vector2) {
        super(vector2);
    }

    public VelocityComponent(float x, float y) {
        super(x, y);
    }

    public boolean isChangeDirection() {
        return changeDirection;
    }

    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }

    public float getSprintSpeed() {
        return sprintSpeed;
    }

    public void setSprintSpeed(float sprintSpeed) {
        this.sprintSpeed = sprintSpeed;
    }
}
