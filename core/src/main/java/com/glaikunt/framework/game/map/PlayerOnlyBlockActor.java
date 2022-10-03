package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;

public class PlayerOnlyBlockActor extends CommonActor {

    private final BodyComponent body;

    public PlayerOnlyBlockActor(ApplicationResources applicationResources, Vector2 pos, Vector2 size) {
        super(applicationResources);

        this.pos.set(pos);
        this.size.set(size);

        this.body = new BodyComponent();
        this.body.setBodyType(BodyType.PLAYER_ONLY_BLOCK);
        this.body.set(getX(), getY(), getWidth(), getHeight());

        getEntity().add(body);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.rect(body.getX(), body.getY(), body.getWidth(), body.getHeight());
    }
}
