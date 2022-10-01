package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;

import java.util.Map;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class PositionIterationsSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);

    public PositionIterationsSystem(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(VelocityComponent.class, PositionComponent.class, BodyComponent.class, SizeComponent.class)
                        .get()
        );
    }

    @Override
    public void update(float delta) {

        for (int ei = 0; ei < entities.size(); ++ei) {

            Entity entity = entities.get(ei);
            VelocityComponent vel = vcm.get(entity);
            PositionComponent pos = pcm.get(entity);
            BodyComponent body = bcm.get(entity);

            pos.x += vel.x;
            pos.y += vel.y;

            body.x = pos.x;
            body.y = pos.y;
        }
    }
}
