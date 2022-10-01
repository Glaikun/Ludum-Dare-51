package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Body;
import com.glaikunt.framework.application.GameUtils;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;

import java.util.Map;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class VelocityIterationsSystem extends EntitySystem {

    private static final float MAX_X_V = 20f;
    private static final float MAX_Y_V = 20f; // should be terminal velocity
    private ImmutableArray<Entity> entities;

    private ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<AccelerationComponent> fcm = ComponentMapper.getFor(AccelerationComponent.class);

    public VelocityIterationsSystem(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(VelocityComponent.class, AccelerationComponent.class, BodyComponent.class)
                        .get()
        );
    }

    @Override
    public void update(float delta) {

        for (int ei = 0; ei < entities.size(); ++ei) {

            Entity entity = entities.get(ei);
            VelocityComponent vel = vcm.get(entity);
            AccelerationComponent accel = fcm.get(entity);

            vel.x = GameUtils.clamp(-MAX_X_V, MAX_X_V, vel.x + (accel.x * delta));
            vel.y = GameUtils.clamp(-MAX_X_V, MAX_X_V, vel.y + (accel.y * delta));
        }
    }
}
