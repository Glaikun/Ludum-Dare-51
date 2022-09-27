package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SizeComponent extends Vector2 implements Component {

    public SizeComponent() {
        super();
    }

    public SizeComponent(Vector2 vector2) {
        super(vector2);
    }

    public SizeComponent(float x, float y) {
        super(x, y);
    }
}
