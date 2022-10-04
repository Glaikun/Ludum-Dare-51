package com.glaikunt.framework.esc.component.camera;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.LinkedList;
import java.util.List;

public class CameraControlsComponent implements Component {

    private boolean enableZoom;
    private boolean enableMovement;
    private final List<OrthographicCamera> cameras = new LinkedList<>();

    private float movementSpeed = 50;
    private float scrollSpeed = 1f;

    public boolean isEnableZoom() {
        return enableZoom;
    }

    public void setEnableZoom(boolean enableZoom) {
        this.enableZoom = enableZoom;
    }

    public boolean isEnableMovement() {
        return enableMovement;
    }

    public void setEnableMovement(boolean enableMovement) {
        this.enableMovement = enableMovement;
    }

    public List<OrthographicCamera> getCameras() {
        return cameras;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getScrollSpeed() {
        return scrollSpeed;
    }

    public void setScrollSpeed(float scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }
}
