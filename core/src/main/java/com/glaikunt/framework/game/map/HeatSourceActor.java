package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.FrameworkConstants;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;
import com.glaikunt.framework.pixels.ExplodePixelActor;

public class HeatSourceActor extends CommonActor {

    private final BodyComponent body;
    private final AnimationComponent animation;

    private final TickTimer pixelsTimer = new TickTimer(1f);

    public HeatSourceActor(ApplicationResources applicationResources, Vector2 pos) {
        super(applicationResources);

        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.HEATSOURCE), 4, 1);
        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight());


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
    public void act(float delta) {

        pixelsTimer.tick(delta);
        if (pixelsTimer.isTimerEventReady()) {

//           float xPos, float yPos, float targetXPos, float targetYPos, float width, float height, Color color, int amount, float speed) {
            float x = MathUtils.random(getX()+5, getX() +(getWidth()));
            float y = MathUtils.random(getY()+5, getY() +(getHeight()/2));
            getStage().addActor(new ExplodePixelActor(getApplicationResources(), x, y, 1, 1, new Color[]{FrameworkConstants.ORANGE, FrameworkConstants.RED}, 15, 5));
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(body.getX(), body.getY(), body.getWidth(), body.getHeight(), Color.RED, Color.ORANGE, Color.RED, Color.ORANGE);
    }
}
