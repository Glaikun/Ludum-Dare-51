package com.glaikunt.framework.esc.component.animation;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;

public class MovementAnimationComponent implements Component {

    private TickTimer rotationTimer;
    private boolean toggle;
    private boolean paused;
    private boolean pixelAnimation;
    private float rotation;
    private float rotationSpeed;

    public void setRotationTimer(TickTimer rotationTimer) {
        this.rotationTimer = rotationTimer;
    }

    public TickTimer getRotationTimer() {
        return rotationTimer;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPixelAnimation() {
        return pixelAnimation;
    }

    public void setPixelAnimation(boolean pixelAnimation) {
        this.pixelAnimation = pixelAnimation;
    }
}
