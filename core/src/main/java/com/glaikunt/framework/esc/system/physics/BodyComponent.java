package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class BodyComponent extends Rectangle implements Component {

    private final List<Rectangle> collidingWith = new LinkedList<>();
    private BodyType bodyType;

    public List<Rectangle> getCollidingWith() {
        return collidingWith;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
