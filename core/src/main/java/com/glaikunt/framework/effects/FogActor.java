package com.glaikunt.framework.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TextureCache;



public class FogActor extends Actor {

    protected final TextureRegion background;
    private float offsetX;
    private float offsetY;
    protected final float targetWidth;
    protected final float targetHeight;
    private final float speed;

    public FogActor(ApplicationResources applicationResources, float speed) {
        this.background = new TextureRegion(applicationResources.getTexture(TextureCache.FOG));
        this.offsetX = 0;
        this.offsetY = 0;
        this.targetWidth = background.getTexture().getWidth();
        this.targetHeight = background.getTexture().getHeight();
        this.speed = speed;
    }

    public void updatePosition(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float amt = delta * speed;
        background.setU(background.getU()+amt);
        background.setU2(background.getU2()+amt);
        background.setV(background.getV()-amt/10f);
        background.setV2(background.getV2()-amt/10f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, offsetX-(targetWidth/2), offsetY-(targetHeight/2), targetWidth, targetHeight);
    }

    @Override
    public float getWidth() {
        return targetWidth;
    }

    @Override
    public float getHeight() {
        return targetHeight;
    }

    @Override
    public String toString() {
        return "FogActor{" +
                "background=" + background +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", targetWidth=" + targetWidth +
                ", targetHeight=" + targetHeight +
                '}';
    }
}

