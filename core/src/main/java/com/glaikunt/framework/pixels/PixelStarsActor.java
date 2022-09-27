package com.glaikunt.framework.pixels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TextureCache;

public class PixelStarsActor extends Actor {

    private TextureRegion pixel;

    private float[] xPos, yPos, xPosVol, yPosVol, angle, energy, width, height;
    private Color[] colour;

    public PixelStarsActor(ApplicationResources applicationResources, Color colour) {

        this.pixel = new TextureRegion(applicationResources.getTexture(TextureCache.PIXEL));
        int index = 1000;
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
            float size = MathUtils.random()*2;
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

    @Override
    public void act(float delta) {

        for (int i = 0; i < this.xPos.length; i++) {
            if (xPos[i] < 0) {
                xPosVol[i] = -xPosVol[i];
            } else if (xPos[i] > Display2D.WORLD_WIDTH) {
                xPosVol[i] = -xPosVol[i];
            }
            if (yPos[i] < 0) {
                yPosVol[i] = -yPosVol[i];
            } else if (yPos[i] > Display2D.WORLD_HEIGHT) {
                yPosVol[i] = -yPosVol[i];
            }

            xPos[i] += (xPosVol[i] * MathUtils.cos(angle[i])) * delta;
            yPos[i] += (yPosVol[i] * MathUtils.sin(angle[i])) * delta;
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
