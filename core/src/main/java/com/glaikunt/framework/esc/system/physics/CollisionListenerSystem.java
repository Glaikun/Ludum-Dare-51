package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class CollisionListenerSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public CollisionListenerSystem(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(VelocityComponent.class, PositionComponent.class)
                        .get()
        );
    }

    //TODO need a before colliding code
    //TODO need a after colliding code
    //TODO need during colliding code
    //TODO what side collided with maybe use Intersector.intersectRectangles(orbRect, playerRect, intersection);

    @Override
    public void update(float delta) {


        for (int ei = 0; ei < entities.size(); ++ei) {

            Entity entity = entities.get(ei);
        }
    }
}
