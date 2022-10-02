package com.glaikunt.framework.esc.component.movement;

import com.badlogic.gdx.math.MathUtils;

import static com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent.Animation.IDLE;

public class EnemyInputComponent extends AbstractPlayerInputComponent {

    private static final boolean CHAOS_MONKEY = false;

    private boolean sprinting = false;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean jump = false;

    @Override
    public void resetStoreInputs() {

    }

    @Override
    public void storeInputs() {

    }

    @Override
    public Animation getAnimation() {
        if (!isAnyMovementKeysPressed()) {
            setAnimation(IDLE);
        }
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public boolean isMovingLeft() {
        return left || (CHAOS_MONKEY && MathUtils.randomBoolean());
    }

    public boolean isMovingRight() {
        return right || (CHAOS_MONKEY && MathUtils.randomBoolean());
    }

    public boolean isMovingUp() {
        return up;
    }

    public boolean isMovingDown() {
        return down;
    }

    @Override
    public boolean isJumping() {
        return jump || (CHAOS_MONKEY && MathUtils.randomBoolean());
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
