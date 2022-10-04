package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.common.WarmthComponent;

import java.util.Map;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class CollisionSystem extends EntitySystem {

    private final ImmutableArray<Entity> entities;

    private final ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<AccelerationComponent> acm = ComponentMapper.getFor(AccelerationComponent.class);
    private final ComponentMapper<WarmthComponent> wcm = ComponentMapper.getFor(WarmthComponent.class);

    public CollisionSystem(Engine engine) {
        this.entities = engine.getEntitiesFor( Family.all(BodyComponent.class, VelocityComponent.class, AccelerationComponent.class).get());
    }

    @Override
    public void update(float delta) {

        for (int e = 0; e < entities.size(); ++e) {

            Entity entity = entities.get(e);
            BodyComponent body = bcm.get(entity);
            VelocityComponent vel = vcm.get(entity);
            AccelerationComponent accel = acm.get(entity);
            WarmthComponent warmth = wcm.get(entity);
            if (warmth != null) {
                warmth.setOutside(true); // default until detected otherwise
                warmth.setNearHeatSource(false); // default until detected otherwise
            }

            for (Map.Entry<BodyComponent, ContactComponent> entry : body.getContactsByBody().entrySet()) {

                BodyComponent key = entry.getKey();
                if (key.getBodyType() == BodyType.ENEMY) {
                    continue;
                } else if (key.getBodyType() == BodyType.CHECKPOINT) {
                    continue;
                } else if (key.getBodyType() == BodyType.CHASM) {
                    if (warmth != null) {
                        warmth.setWarmth(-1);
                        warmth.setOutside(false);
                    }
                    continue;
                } else if (key.getBodyType() == BodyType.INDOORS) {
                    if (warmth != null) {
                        warmth.setOutside(false);
                    }
                    continue;
                } else if (key.getBodyType() == BodyType.HEATSOURCE) {
                    if (warmth != null) {
                        warmth.setNearHeatSource(true);
                    }
                    continue;
                }
                ContactComponent contact = entry.getValue();
                if (contact.getNormal().y < 0 && vel.y < 0) {
                    accel.y = 0;
                    vel.y = 0;
                } else if (contact.getNormal().y > 0 && vel.y > 0) {
                    accel.y = 0;
                    vel.y = 0;
                }

                if (contact.getNormal().x < 0 && vel.x < 0) {
                    vel.x = 0;
                } else if (contact.getNormal().x > 0 && vel.x > 0) {
                    vel.x = 0;
                }
            }
        }
    }
}

