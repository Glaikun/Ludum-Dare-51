package com.glaikunt.framework.application3d.scene;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class Actor {

    protected final Vector3 position;
    protected final Vector3 size;

    private boolean visible;
    private boolean remove;

    private Stage stage;

    public Actor() {

        this.position = new Vector3();
        this.size = new Vector3();
        this.visible = true;
    }

    public void act(float delta) {

    }

    public void draw(ModelBatch batch, Environment environment) {

    }

    public void draw(ModelBatch batch) {

    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.x;
    }

    public float getWidth() {
        return position.x;
    }

    public float getHeight() {
        return position.x;
    }

    public float getDepth() {
        return position.x;
    }

    public float getZ() {
        return position.x;
    }


    public Vector3 getPosition() {
        return position;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Vector3 getSize() {
        return size;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
