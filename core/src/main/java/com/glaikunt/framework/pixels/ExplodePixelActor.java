package com.glaikunt.framework.pixels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TextureCache;

import java.util.Random;

public class ExplodePixelActor extends Actor {

    private TextureRegion pixel;

    private float[] xPos, yPos, xPosVol, yPosVol, angle, energy;
    private float width, height;
    private Color[] colour;

    public ExplodePixelActor(ApplicationResources applicationResources, float xPos, float yPos, Color[] colour, int amount) {
        this(applicationResources, xPos, yPos, 1, 1, colour, amount, .2f);
    }

    public ExplodePixelActor(ApplicationResources applicationResources, float xPos, float yPos, float width, float height, Color[] colour, int amount) {
        this(applicationResources, xPos, yPos, width, height, colour, amount, .2f);
    }

    public ExplodePixelActor(ApplicationResources applicationResources, float xPos, float yPos, float width, float height, Color[] colour, int amount, float vel) {

        this.pixel = new TextureRegion(applicationResources.getTexture(TextureCache.PIXEL));
        this.width = width;
        this.height = height;
        this.xPos = new float[amount];
        this.yPos = new float[amount];
        this.xPosVol = new float[amount];
        this.yPosVol = new float[amount];
        this.angle = new float[amount];
        this.energy = new float[amount];
        this.colour = new Color[amount];

        for (int i = 0; i < this.xPos.length; i++) {
            this.xPos[i] = xPos;
            this.yPos[i] = yPos;
            // * .9 makes a really cool effect
            this.xPosVol[i] = MathUtils.random() * vel;
            this.yPosVol[i] = MathUtils.random() * vel;
            energy[i] = MathUtils.random();

            Color c = colour[MathUtils.random(0, colour.length-1)];
            this.colour[i] = new Color(1.0f, 1.0f, .0f, energy[i]);
            this.colour[i].b = c.b;
            this.colour[i].r = c.r;
            this.colour[i].g = c.g;

            angle[i] = MathUtils.random(0,6);
        }
    }

    @Override
    public void act(float delta) {

        boolean destroy = true;
        for (int i = 0; i < this.xPos.length; i++) {
            energy[i] -= 1 * delta;
            xPos[i] += (xPosVol[i] * MathUtils.cos(angle[i])) * delta;
            yPos[i] += (yPosVol[i] * MathUtils.sin(angle[i])) * delta ;
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
