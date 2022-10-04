package com.glaikunt.framework.esc.component.movement;

import static com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent.Animation.IDLE;

public class EnemyInputComponent extends AbstractPlayerInputComponent {

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
        return left;
    }

    public boolean isMovingRight() {
        return right;
    }

    public boolean isMovingUp() {
        return up;
    }

    public boolean isMovingDown() {
        return down;
    }

    public boolean isJumping() {
        return jump;
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
//        if (left) System.out.println(Ansi.cyan("<< STEP LEFT"));
        this.left = left;
    }

    public void setRight(boolean right) {
//        if (right) System.out.println(Ansi.cyan(">> STEP RIGHT"));
        this.right = right;
    }

    public void setJump(boolean jump) {
//        if (jump) System.out.println(Ansi.cyan("^^ JUMP"));
        this.jump = jump;
    }
}
