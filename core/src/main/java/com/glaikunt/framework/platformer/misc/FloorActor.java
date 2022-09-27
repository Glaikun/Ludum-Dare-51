package com.glaikunt.framework.platformer.misc;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.DynamicDisplay;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;

public class FloorActor extends CommonActor {

    public FloorActor(ApplicationResources applicationResources) {
        super(applicationResources);

        BodyComponent bodyComponent = new BodyComponent();
        bodyComponent.setBodyType(BodyComponent.BODY_TYPE.STATIC);

        this.size.set(DynamicDisplay.WORLD_WIDTH-1, 25);
        this.pos.set(1, 1);

        getEntity().add(bodyComponent);
        getApplicationResources().getEngine().addEntity(getEntity());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.rect(pos.x, pos.y, getWidth(), getHeight());
    }
}
