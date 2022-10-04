package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;

public class BreakableActor extends CommonActor {

    private final BodyComponent body;
    private final AnimationComponent animation;

    public BreakableActor(ApplicationResources applicationResources, Vector2 pos) {
        super(applicationResources);

        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.BREAKABLE_DEBUG), 1, 1);
        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight());

        this.body = new BodyComponent();
        this.body.setBodyType(BodyType.BREAKABLE);
        this.body.set(getX(), getY(), getWidth(), getHeight());

        HealthComponent health = new HealthComponent(100f, 100f);

        getEntity().add(animation);
        getEntity().add(body);
        getEntity().add(health);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(body.getX(), body.getY(), body.getWidth(), body.getHeight(), Color.BLACK, Color.YELLOW, Color.BLACK, Color.YELLOW);
    }
}
