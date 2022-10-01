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
    private ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);

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
            BodyComponent body = bcm.get(entity);

            vel.x = GameUtils.clamp(-MAX_X_V, MAX_X_V, vel.x + (accel.x * delta));
            vel.y = GameUtils.clamp(-MAX_X_V, MAX_X_V, vel.y + (accel.y * delta));

            for (Map.Entry<BodyComponent, ContactComponent> e : body.getContactsByBody().entrySet()) {
                // TODO test the normal here? e.g. x = 0 when hit the sides of a hole
                if (e.getValue().getNormal().y < 0 || e.getValue().getNormal().y > 0) {
                    // not falling
                    vel.y = 0;
                }
                if (e.getValue().getNormal().x < 0 || e.getValue().getNormal().x > 0) {
                    // not moving
                    vel.x = 0;
                }
            }
        }
    }
}
