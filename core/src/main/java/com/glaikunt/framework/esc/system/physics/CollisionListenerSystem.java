package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.GameUtils;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class CollisionListenerSystem extends EntitySystem {

    private ImmutableArray<Entity> allBodyEntities, bodyEntitiesWithVel;

    private ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);

    private final Rectangle tmpBodyA = new Rectangle();
    private final Rectangle tmpBodyB = new Rectangle();
    private final Rectangle tmpContact = new Rectangle();
    private final Vector2 tmpVecA = new Vector2();
    private final Vector2 tmpVecB = new Vector2();
    private final Vector2 tmpVecContact = new Vector2();

    public CollisionListenerSystem(Engine engine) {
        this.allBodyEntities = engine.getEntitiesFor(Family.all(BodyComponent.class).get());
        this.bodyEntitiesWithVel = engine.getEntitiesFor( Family.all(BodyComponent.class, VelocityComponent.class).get());
    }

    //TODO need a before colliding code
    //TODO need a after colliding code
    //TODO need during colliding code
    //TODO what side collided with maybe use Intersector.intersectRectangles(orbRect, playerRect, intersection);

    @Override
    public void update(float delta) {

        for (int eiB = 0; eiB < allBodyEntities.size(); ++eiB) {

            Entity entityB = allBodyEntities.get(eiB);
            BodyComponent body = bcm.get(entityB);

            if (body.getAfterContacts().isEmpty() || body.getBeforeContacts().isEmpty()) {
                body.getAfterContacts().clear();
                body.getBeforeContacts().clear();
            }
        }

        for (int eiA = 0; eiA < bodyEntitiesWithVel.size(); ++eiA) {

            Entity entityA = bodyEntitiesWithVel.get(eiA);
            BodyComponent bodyA = bcm.get(entityA);
            VelocityComponent velA = vcm.get(entityA);

            for (int eiB = 0; eiB < allBodyEntities.size(); ++eiB) {

                Entity entityB = allBodyEntities.get(eiB);
                BodyComponent bodyB = bcm.get(entityB);

                if (bodyA.equals(bodyB)) {
                    continue;
                }

                tmpBodyA.set(bodyA);
                tmpBodyB.set(bodyB);

                tmpBodyA.x += velA.x;
                tmpBodyA.y += velA.y;

                if (!bodyA.getBodyContacts().contains(bodyB) && Intersector.intersectRectangles(tmpBodyA, tmpBodyB, tmpContact)) {

                    ContactComponent contact = new ContactComponent();
                    contact.setBodyA(bodyA);
                    contact.setBodyB(bodyB);

                    tmpBodyA.getCenter(tmpVecA);
//                    tmpBodyB.getCenter(tmpVecB);
                    tmpContact.getCenter(tmpVecContact);
                    tmpVecContact.sub(tmpVecA); // calculate
                    contact.getNormal().x = GameUtils.clamp(-1, 1, tmpVecContact.x);
                    contact.getNormal().y = GameUtils.clamp(-1, 1, tmpVecContact.y);

                    Gdx.app.log("DEBUG", "subtracted vec: " +   contact.getNormal());

                    bodyA.getBeforeContacts().add(contact);
                    bodyB.getBeforeContacts().add(contact);

                    bodyA.getBodyContacts().add(bodyB);
                    bodyB.getBodyContacts().add(bodyA);

                }
                if (bodyA.getBodyContacts().contains(bodyB) && !Intersector.intersectRectangles(tmpBodyA, tmpBodyB, tmpContact)) {

                    ContactComponent contact = new ContactComponent();
                    contact.setBodyA(bodyA);
                    contact.setBodyB(bodyB);

                    bodyA.getAfterContacts().add(contact);
                    bodyB.getAfterContacts().add(contact);

                    bodyA.getBodyContacts().remove(bodyB);
                    bodyB.getBodyContacts().remove(bodyA);
                }
            }
        }
    }
}
