package com.glaikunt.framework.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.GravityComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;
import com.glaikunt.framework.game.GameConstants;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerActor extends CommonActor {

    private final AccelerationComponent acceleration;
    private final VelocityComponent velocity;
    private final AnimationComponent animation;
    private final PlayerInputComponent playerInput;
    private final BodyComponent body;

    private final Vector2 tmpVector2 = new Vector2();

    public PlayerActor(ApplicationResources applicationResources, Vector2 pos) {
        super(applicationResources);

        this.acceleration = new AccelerationComponent();
        this.velocity = new VelocityComponent();
        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER), 1, 1);
        this.playerInput = new PlayerInputComponent();

        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth()-1, animation.getCurrentFrame().getRegionHeight()-1);

        this.body = new BodyComponent();
        this.body.setBodyType(BodyType.DYNAMIC);
        this.body.set(getX(), getY(), getWidth(), getHeight());

        getEntity().add(acceleration);
        getEntity().add(velocity);
        getEntity().add(animation);
        getEntity().add(playerInput);
        getEntity().add(body);
        getEntity().add(getApplicationResources().getGlobalEntity().getComponent(GravityComponent.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {

        cameraUpdate(delta);

        if (!getBody().getBeforeContacts().isEmpty()) {
            Gdx.app.log("DEBUG", "Before Collide Intersection: " + getBody().getBeforeContacts().size() + ", and body contacts is now: " + getBody().getContactsByBody().size());
            List<Vector2> collect = getBody().getBeforeContacts().stream().map(ContactComponent::getNormal)
                    .collect(Collectors.toList());
            Gdx.app.log("DEBUG", "Before Collide normal mappings: " + collect);
        }
        if (!getBody().getAfterContacts().isEmpty()) {
            Gdx.app.log("DEBUG", "After Collide Intersection: " + getBody().getAfterContacts().size() + ", and body contacts is now: " + getBody().getContactsByBody().size());
            List<Vector2> collect = getBody().getAfterContacts().stream().map(ContactComponent::getNormal)
                    .collect(Collectors.toList());
            Gdx.app.log("DEBUG", "After Collide normal mappings: " + collect);
        }
    }

    private void cameraUpdate(float delta) {
        getStage().getCamera().position.x = MathUtils.lerp(getStage().getCamera().position.x, getX() + (getWidth() / 2), 5 * delta);
        getStage().getCamera().position.y = MathUtils.lerp(getStage().getCamera().position.y, (getY()) + (getHeight()*3), 5 * delta);

        float newZoom = GameConstants.ZOOM
                + tmpVector2.set(getStage().getCamera().position.x, getStage().getCamera().position.y)
                .sub(getX() + (getWidth() / 2), getY() + (getHeight()*3)).scl(.001f)
                .len();
        ((OrthographicCamera) getStage().getCamera()).zoom = MathUtils.lerp(((OrthographicCamera) getStage().getCamera()).zoom, newZoom, 1 * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.rect(getBodyRect().getX(), getBodyRect().getY(), getBodyRect().width, getBodyRect().height);
    }

    public BodyComponent getBody() {
        return body;
    }

    public Rectangle getBodyRect() {
        return body;
    }
}
