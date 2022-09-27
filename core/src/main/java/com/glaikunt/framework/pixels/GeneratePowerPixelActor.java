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

public class GeneratePowerPixelActor extends Actor {

    private TextureRegion pixel;

    private float[] xPos, yPos, xPosVol, yPosVol, angle, energy;
    private float width, height;
    private Color[] colour;
    private float targetXPos;
    private float targetYPos;
    private boolean destoyed = false;
    private int range = 8;

    public GeneratePowerPixelActor(ApplicationResources applicationResources, float xPos, float yPos, Color[] colour, int amount) {
        this(applicationResources, xPos, yPos, 1, 1, colour, amount, .2f);
    }

    public GeneratePowerPixelActor(ApplicationResources applicationResources, float xPos, float yPos, float width, float height, Color[] colour, int amount) {
        this(applicationResources, xPos, yPos, width, height, colour, amount, .2f);
    }

    public GeneratePowerPixelActor(ApplicationResources applicationResources, float xPos, float yPos, float width, float height, Color[] colour, int amount, float vel) {

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

        targetXPos = (xPos+((range/2f)));
        targetYPos = (yPos+((range/2f)));

        Random r = new Random();
        for (int i = 0; i < this.xPos.length; i++) {

            float xRange = (float)r.nextDouble() * ((xPos+(range)) - xPos) + xPos;
            float yRange = (float)r.nextDouble() * ((yPos+(range)) - yPos) + yPos;

            this.xPos[i] = xRange;
            this.yPos[i] = yRange;
            float a = MathUtils.random(10, 25) ;
//            float a = MathUtils.random() * .5f;
            this.xPosVol[i] = a;
            this.yPosVol[i] = a;
            energy[i] = MathUtils.random();

            Color c = colour[MathUtils.random(0, colour.length-1)];
            this.colour[i] = new Color(c);

            angle[i] = r.nextInt(6) + 1;
        }
    }

    @Override
    public void act(float delta) {

        boolean destroy = true;
        for (int i = 0; i < this.xPos.length; i++) {
            energy[i] -= 1 * delta;
            if (xPos[i] < targetXPos-(1)) {
                xPos[i] += xPosVol[i] * delta ;
            } else if (xPos[i] > targetXPos+(1)) {
                xPos[i] -= xPosVol[i] * delta;
            }

            if (yPos[i] < targetYPos-(1)) {
                yPos[i] += yPosVol[i] *delta;
            } else if (yPos[i] > targetYPos+(1)) {
                yPos[i] -= yPosVol[i] * delta;
            }
            if (energy[i] > 0) {
                destroy = false;
            }
        }

        if (destroy) {
            Gdx.app.log("INFO", "Removing " + getClass().getSimpleName());
            remove();
            this.destoyed = true;
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

    public boolean isDestroyed() {
        return destoyed;
    }

    public int getRange() {
        return range;
    }
}
