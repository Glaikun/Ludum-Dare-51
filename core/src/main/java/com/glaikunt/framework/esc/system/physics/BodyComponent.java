package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class BodyComponent implements Component {

    private List<Rectangle> collidingWith = new LinkedList<>();
    private Rectangle body;

    public List<Rectangle> getCollidingWith() {
        return collidingWith;
    }

    public Rectangle getBody() {
        return body;
    }
}
