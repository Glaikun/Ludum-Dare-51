package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;

import java.util.Map;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class CollisionSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);

    public CollisionSystem(Engine engine) {
        this.entities = engine.getEntitiesFor( Family.all(BodyComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float delta) {

        for (int e = 0; e < entities.size(); ++e) {

            Entity entity = entities.get(e);
            BodyComponent body = bcm.get(entity);
            VelocityComponent vel = vcm.get(entity);
            PositionComponent pos = pcm.get(entity);

            for (Map.Entry<BodyComponent, ContactComponent> entry : body.getContactsByBody().entrySet()) {

                BodyComponent key = entry.getKey();
                ContactComponent contact = entry.getValue();


                // TODO test the normal here? e.g. x = 0 when hit the sides of a hole
                if (contact.getNormal().y < 0 || contact.getNormal().y > 0) {
                    vel.y = 0;
                }
                if (contact.getNormal().x < 0 || contact.getNormal().x > 0) {
                    vel.x = 0;
                }
            }
        }
    }
}

