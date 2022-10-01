package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;

public class HeatSourceActor extends CommonActor {

    private final BodyComponent body;
    private final AnimationComponent animation;

    public HeatSourceActor(ApplicationResources applicationResources, Vector2 pos) {
        super(applicationResources);

        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.HEATSOURCE), 1, 1);
        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth()-1, animation.getCurrentFrame().getRegionHeight()-1);


        this.body = new BodyComponent();
        this.body.setBodyType(BodyType.HEATSOURCE);
        this.body.set(getX(), getY(), getWidth(), getHeight());

        getEntity().add(animation);
        getEntity().add(body);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(body.getX(), body.getY(), body.getWidth(), body.getHeight(), Color.RED, Color.ORANGE, Color.RED, Color.ORANGE);
    }
}
