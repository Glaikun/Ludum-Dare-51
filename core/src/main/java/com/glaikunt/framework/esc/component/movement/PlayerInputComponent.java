package com.glaikunt.framework.esc.component.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;

import static com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent.Animation.IDLE;

public class PlayerInputComponent extends AbstractPlayerInputComponent implements InputProcessor {

    private static final boolean CHAOS_MONKEY = false;


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
        return Gdx.input.isKeyPressed(moveLeftPrimaryKey) || Gdx.input.isKeyPressed(moveLeftSecondaryKey) || (CHAOS_MONKEY && MathUtils.randomBoolean());
    }

    public boolean isMovingRight() {
        return Gdx.input.isKeyPressed(moveRightPrimaryKey) || Gdx.input.isKeyPressed(moveRightSecondaryKey) || (CHAOS_MONKEY && MathUtils.randomBoolean());
    }

    public boolean isMovingUp() {
        return Gdx.input.isKeyPressed(moveUpKeyPrimary) || Gdx.input.isKeyPressed(moveUpKeySecondary);
    }

    public boolean isMovingDown() {
        return Gdx.input.isKeyPressed(moveDownKeyPrimary) || Gdx.input.isKeyPressed(moveDownKeySecondary);
    }

    @Override
    public boolean isJumping() {
        return Gdx.input.isKeyPressed(jumpKeyPrimary) || (CHAOS_MONKEY && MathUtils.randomBoolean());
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
