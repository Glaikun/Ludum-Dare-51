package com.glaikunt.framework.esc.component.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.core.Component;

public class GravityComponent extends Vector2 implements Component {

    public GravityComponent() {
        super();
    }

    public GravityComponent(Vector2 vector2) {
        super(vector2);
    }

    public GravityComponent(float xPos, float yPos) {
        super(xPos, yPos);
    }
}
