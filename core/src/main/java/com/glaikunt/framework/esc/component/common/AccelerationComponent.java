package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class AccelerationComponent extends Vector2 implements Component {

    public AccelerationComponent() {
        super();
    }

    public AccelerationComponent(Vector2 vector2) {
        super(vector2);
    }

    public AccelerationComponent(float xPos, float yPos) {
        super(xPos, yPos);
    }
}
