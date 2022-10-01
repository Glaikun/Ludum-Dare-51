package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class VelocityDecaySystem extends EntitySystem {

    private static final float DECAY_RATE = 10f;
    private final ImmutableArray<Entity> entities;

    private ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);


    public VelocityDecaySystem(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(VelocityComponent.class, AccelerationComponent.class)
                        .get()
        );
    }

    @Override
    public void update(float delta) {

        for (int ei = 0; ei < entities.size(); ++ei) {

            Entity entity = entities.get(ei);
            VelocityComponent vel = vcm.get(entity);


            vel.scl(1f - (DECAY_RATE*delta) );
//            accel.scl(1f - (100*DECAY_RATE*delta) );
//            accel.x -= Math.abs(accel.x/2);// * delta;
//            accel.y -= (accel.x/8) * delta;
        }
    }
}
