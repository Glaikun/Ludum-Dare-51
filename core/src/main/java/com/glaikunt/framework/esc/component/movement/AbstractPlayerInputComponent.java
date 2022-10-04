package com.glaikunt.framework.esc.component.movement;

import com.badlogic.ashley.core.Component;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SPACE;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

public abstract class AbstractPlayerInputComponent implements Movable, Component {

    protected Animation animation = Animation.IDLE;
    protected Direction facing = Direction.DOWN;
    protected Animation forceAnimation = null;
    private boolean disableInputMovement;
    private boolean walkRight;

    protected boolean reset;

    protected int moveLeftPrimaryKey = A;
    protected int moveLeftSecondaryKey = LEFT;
    protected int moveRightPrimaryKey = D;
    protected int moveRightSecondaryKey = RIGHT;
    protected int moveUpKeyPrimary = W;
    protected int moveUpKeySecondary = UP;
    protected int moveDownKeyPrimary = S;
    protected int moveDownKeySecondary = DOWN;
    protected int jumpKeyPrimary = SPACE;
    protected int sprintKeyPrimary = SHIFT_LEFT;

    public abstract void resetStoreInputs();

    public abstract void storeInputs();

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public enum Animation {
        IDLE,
        MOVEMENT,
        FALLING,
        JUMP
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public boolean isMovingDiagonally() {
        return (isMovingUp() || isMovingDown()) && (isMovingLeft() || isMovingRight());
    }

    protected boolean isAnyMovementKeysPressed() {
        return isMovingLeft() || isMovingRight() || isMovingUp() || isMovingDown();
    }

    public void setForceAnimation(Animation forceAnimation) {
        this.forceAnimation = forceAnimation;
    }

    public Animation getForceAnimation() {
        return forceAnimation;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isDisableInputMovement() {
        return disableInputMovement;
    }

    public void setDisableInputMovement(boolean disableInputMovement) {
        this.disableInputMovement = disableInputMovement;
    }

    public boolean isWalkRight() {
        return walkRight;
    }

    public void setWalkRight(boolean walkRight) {
        this.walkRight = walkRight;
    }
}
