package com.glaikunt.framework.pixels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TextureCache;

public class FlamePixelActor extends Actor {

    private final TextureRegion pixel;

    private final float[] xPos;
    private final float[] yPos;
    private final float[] xPosVol;
    private final float[] yPosVol;
    private final float[] angle;
    private final float[] energy;
    private float width = 2;
    private float height = 2;
    private final Color[] colour;

    public FlamePixelActor(ApplicationResources applicationResources, float xPos, float yPos, float targetXPos, float targetYPos, float width, float height, Color color, int amount) {
        this(applicationResources, xPos, yPos, targetXPos, targetYPos, width, height, color, amount, .4f);
    }

    public FlamePixelActor(ApplicationResources applicationResources, float xPos, float yPos, float targetXPos, float targetYPos, float width, float height, Color color, int amount, float speed) {

        this.pixel = new TextureRegion(applicationResources.getTexture(TextureCache.PIXEL));
        this.xPos = new float[amount];
        this.yPos = new float[amount];
        this.xPosVol = new float[amount];
        this.yPosVol = new float[amount];
        this.angle = new float[amount];
        this.energy = new float[amount];
        this.colour = new Color[amount];
        this.width = width;
        this.height = height;

        for (int i = 0; i < this.xPos.length; i++) {
            this.xPos[i] = xPos;
            this.yPos[i] = yPos;
            this.xPosVol[i] = MathUtils.random() * speed;
            this.yPosVol[i] = MathUtils.random() * speed;

            energy[i] = MathUtils.random();
            colour[i] = new Color(color.r, color.g, color.b, energy[i]);

            float xDiff = -(xPos - targetXPos);
            float yDiff = -(yPos - targetYPos);

            this.angle[i] = (float) ((Math.atan2(yDiff, xDiff)));
            if (angle[i] == 0) {
                angle[i] = .1f;
            }
        }
    }

    @Override
    public void act(float delta) {

        boolean destroy = true;
        for (int i = 0; i < this.xPos.length; i++) {
            energy[i] -= 1 * delta;
            xPos[i] += (xPosVol[i] * MathUtils.cos(angle[i])) * delta;
            yPos[i] += (yPosVol[i] * MathUtils.sin(angle[i])) * delta;
            if (energy[i] > 0) {
                destroy = false;
            }
        }

        if (destroy) {
            Gdx.app.log("INFO", "Removing " + getClass().getSimpleName());
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < this.xPos.length; i++) {
            colour[i].a = energy[i] <= 0 ? 0 : energy[i];
            batch.setColor(colour[i]);
            batch.draw(pixel, xPos[i], yPos[i], width / 2, height / 2, width, height, 1, 1, angle[i]);
        }
        batch.setColor(1, 1, 1, 1);
    }
}
