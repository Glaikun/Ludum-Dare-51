package com.glaikunt.framework.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TextureCache;

public class FadeActor extends CommonActor {

    private final Texture pixel;
    private float fade = 0;

    public FadeActor(ApplicationResources applicationResources) {
        super(applicationResources);
        pixel = new Texture(Gdx.files.internal(TextureCache.PIXEL));
        setColor(Color.WHITE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(getColor().r, getColor().r, getColor().r, fade);
        batch.draw(pixel, 0, 0, Display2D.WORLD_WIDTH, Display2D.WORLD_HEIGHT);
    }

    @Override
    public void act(float delta) {

        fade += Math.min(1, .5f * delta);
    }

    public boolean isComplete() {
        return fade >= 1;
    }


}
