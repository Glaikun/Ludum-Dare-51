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
    private ComponentMapper<SizeComponent> scm = ComponentMapper.getFor(SizeComponent.class);
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
            SizeComponent size = scm.get(entity);
            BodyComponent body = bcm.get(entity);

            pos.x += vel.x;
            pos.y += vel.y;

            for (Map.Entry<BodyComponent, ContactComponent> e : body.getContactsByBody().entrySet()) {

                BodyComponent contextBody = e.getKey();
                ContactComponent contact = e.getValue();

                if (contact.getNormal().y < 0) {
                    pos.y = contextBody.getY()+(size.y-.1f);
                } else if (contact.getNormal().y > 0) {
                    pos.y = contextBody.getY()-(size.y-.1f);
                }

                if (contact.getNormal().x > 0) {
                    pos.x = contextBody.getX()-(size.x+.1f);
                } else if (contact.getNormal().x < 0) {
                    pos.x = contextBody.getX()+(size.x+.1f);
                }
//                else if (e.getValue().getNormal().x > 0) {
//                    pos.x = contextBody.getX()+(contextBody.getWidth()-.1f);
//                }

//                if (e.getValue().getNormal().y < 0 || e.getValue().getNormal().y > 0) {
//                    // not falling
//                    if (e.getValue().getNormal().x < 0 || e.getValue().getNormal().x > 0) {
//                        // not moving
//                        pos.x = pos.x - (pos.x % 32);
//                    }
//                }
//                if (e.getValue().getNormal().x < 0 || e.getValue().getNormal().x > 0) {
//                    // not moving
//
//                }
            }
        }
    }
}
