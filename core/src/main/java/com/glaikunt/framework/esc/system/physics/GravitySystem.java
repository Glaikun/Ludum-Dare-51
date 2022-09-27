package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.GravityComponent;
import com.glaikunt.framework.esc.component.common.MassComponent;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class GravitySystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<GravityComponent> gcm = ComponentMapper.getFor(GravityComponent.class);
    private ComponentMapper<AccelerationComponent> fcm = ComponentMapper.getFor(AccelerationComponent.class);
    private ComponentMapper<MassComponent> mcm = ComponentMapper.getFor(MassComponent.class);

    public GravitySystem(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(GravityComponent.class, AccelerationComponent.class, MassComponent.class)
                        .get()
        );
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            GravityComponent gravity = gcm.get(entity);
            AccelerationComponent accel = fcm.get(entity);
            MassComponent mass = mcm.get(entity);

            accel.y += mass.getMass() * (gravity.y * delta);
        }
    }
}
