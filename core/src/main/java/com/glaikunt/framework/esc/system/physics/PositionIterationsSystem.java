package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.application.GameUtils;
import com.glaikunt.framework.esc.component.common.*;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class PositionIterationsSystem extends EntitySystem {

    private final ImmutableArray<Entity> entities;

    private final ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<WarmthComponent> wcm = ComponentMapper.getFor(WarmthComponent.class);

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
            WarmthComponent warmth = wcm.get(entity);

            if (warmth != null) {
                if (!warmth.isFrozen()) {
                    pos.x += vel.x * GameUtils.clamp(0.05f, 1f, warmth.getWarmthFloat()*2);
                } else {
                    pos.x += vel.x * .05f;
                }
            } else {
                pos.x += vel.x;
            }
            pos.y += vel.y;

            body.x = pos.x;
            body.y = pos.y;
        }
    }
}
