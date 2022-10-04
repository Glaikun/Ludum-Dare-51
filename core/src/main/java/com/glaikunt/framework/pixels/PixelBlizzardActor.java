package com.glaikunt.framework.pixels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TextureCache;

public class PixelBlizzardActor extends Actor {

    private final TextureRegion pixel;

    private final float[] xPos;
    private final float[] yPos;
    private final float[] xPosVol;
    private final float[] yPosVol;
    private final float[] angle;
    private final float[] energy;
    private final float[] width;
    private final float[] height;
    private final Color[] colour;

    private float globalXOffset;
    private float globalYOffset;

    public PixelBlizzardActor(ApplicationResources applicationResources, Color colour) {

        this.pixel = new TextureRegion(applicationResources.getTexture(TextureCache.SNOWFLAKE));
        int index = 500;
        this.xPos = new float[index];
        this.yPos = new float[index];
        this.xPosVol = new float[index];
        this.yPosVol = new float[index];
        this.angle = new float[index];
        this.colour = new Color[index];
        this.energy = new float[index];
        this.width = new float[index];
        this.height = new float[index];

        for (int i = 0; i < this.xPos.length; i++) {
            this.xPos[i] = MathUtils.random()* Display2D.WORLD_WIDTH;
            this.yPos[i] = MathUtils.random()* Display2D.WORLD_HEIGHT;
            this.xPosVol[i] = MathUtils.random() * 5f;
            this.yPosVol[i] = MathUtils.random() * 5f;
            energy[i] = MathUtils.random();
            float size = MathUtils.random()*5;
            width[i] = size;
            height[i] = size;

            this.colour[i] = colour;

            if (i != 0) {
                angle[i] = angle[i-1] + 0.2f;
            } else {
                angle[i] = 0.2f;
            }
            this.colour[i].a = 0.2f;
        }
    }

    public void updatePosition(float x, float y) {
        globalXOffset = x;
        globalYOffset = y;
    }

    @Override
    public void act(float delta) {

        // TODO suspect use of globalOffset here taht might not be entirely accurate
        for (int i = 0; i < this.xPos.length; i++) {
            if (xPos[i] < globalXOffset-(Display2D.WORLD_WIDTH/2)) {
                xPosVol[i] = -xPosVol[i];
                this.xPos[i] = globalXOffset+(Display2D.WORLD_WIDTH/2);
                this.yPos[i] = MathUtils.random(globalYOffset/2, Display2D.WORLD_HEIGHT*2);

            } else if (xPos[i] > globalXOffset+(Display2D.WORLD_WIDTH/2)) {
                xPosVol[i] = -xPosVol[i];
            }
            if (yPos[i] < 0) { // TODO especially this

                yPosVol[i] = -yPosVol[i];
                this.xPos[i] = globalXOffset+(Display2D.WORLD_WIDTH/2);
                this.yPos[i] = MathUtils.random(globalYOffset/2, Display2D.WORLD_HEIGHT*2);
            } else if (yPos[i] > globalYOffset+Display2D.WORLD_HEIGHT) {
                yPosVol[i] = -yPosVol[i];
            }

            angle[i] += 10*delta;
            xPos[i] -= 500 * delta;//(xPosVol[i] * MathUtils.cos(angle[i])) * delta;
            yPos[i] -= 50 * delta;//(yPosVol[i] * MathUtils.sin(angle[i])) * delta;
            this.colour[i].a -= delta;
            if (this.colour[i].a < 0) {
                this.colour[i].a = 0;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < this.xPos.length; i++) {
            colour[i].a = energy[i] <= 0 ? 0 : energy[i];
            batch.setColor(colour[i]);
            batch.draw(pixel, xPos[i], yPos[i], width[i] / 2, height[i] / 2, width[i], height[i], 1, 1, angle[i]);
        }
        batch.setColor(1, 1, 1, 1);
    }
}
