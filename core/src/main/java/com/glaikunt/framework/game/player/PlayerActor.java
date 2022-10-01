package com.glaikunt.framework.game.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;

public class PlayerActor extends CommonActor {

    private final AccelerationComponent acceleration;
    private final VelocityComponent velocity;
    private final AnimationComponent animation;
    private final PlayerInputComponent playerInput;

    public PlayerActor(ApplicationResources applicationResources, Vector2 pos) {
        super(applicationResources);

        this.acceleration = new AccelerationComponent();
        this.velocity = new VelocityComponent();
        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER), 1, 1);
        this.playerInput = new PlayerInputComponent();

        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight());
        this.velocity.set(15, 15);

        getEntity().add(acceleration);
        getEntity().add(velocity);
        getEntity().add(animation);
        getEntity().add(playerInput);
//        getEntity().add(getApplicationResources().getGlobalEntity().getComponent(GravityComponent.class));

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
    }
}
