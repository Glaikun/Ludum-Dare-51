package com.glaikunt.framework.platformer.misc;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;

public class AppleActor extends CommonActor {

    public AppleActor(ApplicationResources applicationResources) {
        super(applicationResources);

//        this.pos.set(DynamicDisplay.WORLD_WIDTH/2, DynamicDisplay.WORLD_WIDTH/2);
//        GravityComponent gravityComponent = new GravityComponent(0, -9.8f);
//        AccelerationComponent accelerationComponent = new AccelerationComponent();
//        VelocityComponent velocityComponent = new VelocityComponent();
//
//        MassComponent massComponent = new MassComponent();
//        massComponent.setMass(.1f);
//
//        BodyComponent bodyComponent = new BodyComponent();
//        bodyComponent.setBodyType(BodyComponent.BODY_TYPE.STATIC);
//
//        getEntity().add(gravityComponent);
//        getEntity().add(accelerationComponent);
//        getEntity().add(velocityComponent);
//        getEntity().add(massComponent);
//        getEntity().add(bodyComponent);
//        applicationResources.getEngine().addEntity(getEntity());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.circle(pos.x, pos.y, 6);
    }
}
