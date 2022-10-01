package com.glaikunt.framework.esc.component.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.esc.system.physics.PhysicConstants;

public class GravityComponent extends Vector2 implements Component {

    public GravityComponent() {
        this.y = PhysicConstants.GRAVITY;
        this.x = PhysicConstants.WIND;
    }
}
