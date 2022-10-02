package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.GameUtils;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;


/**
 * This is gravity movement based on pixels.
 * By Andrew Murray
 */
public class CollisionListenerSystem extends EntitySystem {

    private final ImmutableArray<Entity> allBodyEntities;
    private final ImmutableArray<Entity> bodyEntitiesWithVel;

    private final ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);

    private final Rectangle tmpBodyA = new Rectangle();
    private final Rectangle tmpBodyB = new Rectangle();
    private final Rectangle tmpContact = new Rectangle();
    private final Vector2 tmpVecA = new Vector2();
    private final Vector2 tmpVecB = new Vector2();
    private final Vector2 tmpVecContact = new Vector2();

    public CollisionListenerSystem(Engine engine) {
        this.allBodyEntities = engine.getEntitiesFor(Family.all(BodyComponent.class).get());
        this.bodyEntitiesWithVel = engine.getEntitiesFor(Family.all(BodyComponent.class, VelocityComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float delta) {

        for (int eiB = 0; eiB < allBodyEntities.size(); eiB++) {

            Entity entityB = allBodyEntities.get(eiB);
            BodyComponent body = bcm.get(entityB);

            if (!body.getAfterContacts().isEmpty() || !body.getBeforeContacts().isEmpty()) {
                body.getAfterContacts().clear();
                body.getBeforeContacts().clear();
            }
        }

        for (int eiA = 0; eiA < bodyEntitiesWithVel.size(); eiA++) {

            Entity entityA = bodyEntitiesWithVel.get(eiA);
            BodyComponent bodyA = bcm.get(entityA);
            VelocityComponent velA = vcm.get(entityA);

            for (int eiB = 0; eiB < allBodyEntities.size(); eiB++) {

                Entity entityB = allBodyEntities.get(eiB);
                BodyComponent bodyB = bcm.get(entityB);

                if (bodyA == bodyB) {
                    continue;
                }

                tmpBodyA.set(bodyA);
                tmpBodyB.set(bodyB);

                tmpBodyA.x += velA.x;
                tmpBodyA.y += velA.y;

                if (!bodyA.getContactsByBody().containsKey(bodyB) && Intersector.intersectRectangles(tmpBodyA, tmpBodyB, tmpContact)) {

                    ContactComponent contact = new ContactComponent();
                    contact.setBodyA(bodyA);
                    contact.setBodyB(bodyB);
                    contact.setBodyAType(bodyA.getBodyType());
                    contact.setBodyBType(bodyB.getBodyType());

                    tmpBodyA.getCenter(tmpVecA);
//                    tmpBodyB.getCenter(tmpVecB);
                    tmpContact.getCenter(tmpVecContact);
                    tmpVecContact.sub(tmpVecA); // calculate
                    if (MathUtils.floor(tmpVecContact.x) != 0 && MathUtils.floor(tmpVecContact.y) != 0) {

                        if (Math.abs(tmpVecContact.x) > Math.abs(tmpVecContact.y)) {
                            contact.getNormal().x = GameUtils.clamp(-1, 1, MathUtils.floor(tmpVecContact.x));
                            contact.getNormal().y = 0;
                            Gdx.app.log("DEBUG", bodyA.getBodyType() + " x contact normal: " + contact.getNormal());
                        } else {
                            contact.getNormal().y = GameUtils.clamp(-1, 1, MathUtils.floor(tmpVecContact.y));
                            contact.getNormal().x = 0;
                            Gdx.app.log("DEBUG", bodyA.getBodyType() + " y contact normal: " + contact.getNormal());
                        }
                    } else {
                        contact.getNormal().x = GameUtils.clamp(-1, 1, MathUtils.floor(tmpVecContact.x));
                        contact.getNormal().y = GameUtils.clamp(-1, 1, MathUtils.floor(tmpVecContact.y));
                        Gdx.app.log("DEBUG", bodyA.getBodyType() + " x & y contact normal: " + contact.getNormal());
                    }
                    contact.setInteraction(tmpContact);

                    bodyA.getBeforeContacts().add(contact);
                    bodyA.getContactsByBody().put(bodyB, contact);

//                    Gdx.app.log("DEBUG", bodyA.getBodyType()+" putting ContactsByBody with "+bodyB.getBodyType()+" "+bodyB+" => "+contact);
                }

                if (bodyA.getContactsByBody().containsKey(bodyB) && !tmpBodyA.intersects(tmpBodyB)) {

                    bodyA.getContactsByBody().remove(bodyB);

                    ContactComponent contact = new ContactComponent();
                    contact.setBodyA(bodyA);
                    contact.setBodyB(bodyB);
                    contact.setBodyAType(bodyA.getBodyType());
                    contact.setBodyBType(bodyB.getBodyType());
                    bodyA.getAfterContacts().add(contact);
                }
            }
        }
    }
}

