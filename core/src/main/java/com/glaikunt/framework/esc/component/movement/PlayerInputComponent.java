package com.glaikunt.framework.esc.component.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import static com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent.Animation.IDLE;

public class PlayerInputComponent extends AbstractPlayerInputComponent implements InputProcessor {

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public void resetStoreInputs() {

    }

    @Override
    public void storeInputs() {

    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean isSprinting() {
        return Gdx.input.isKeyPressed(sprintKeyPrimary);
    }

    public boolean isMovingLeft() {
        return Gdx.input.isKeyPressed(moveLeftPrimaryKey) || Gdx.input.isKeyPressed(moveLeftSecondaryKey);
    }

    public boolean isMovingRight() {
        return Gdx.input.isKeyPressed(moveRightPrimaryKey) || Gdx.input.isKeyPressed(moveRightSecondaryKey);
    }

    public boolean isMovingUp() {
        return Gdx.input.isKeyPressed(moveUpKeyPrimary) || Gdx.input.isKeyPressed(moveUpKeySecondary);
    }

    public boolean isMovingDown() {
        return Gdx.input.isKeyPressed(moveDownKeyPrimary) || Gdx.input.isKeyPressed(moveDownKeySecondary);
    }

    @Override
    public boolean keyUp(int i) {
        if (!isAnyMovementKeysPressed()) {
            setAnimation(IDLE);
        }

//        if (i == attackPrimary) {
//            forceAnimation = HEAVY_ATTACK;
//            setReset(true);
//        }

        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
