package com.glaikunt.framework.game.enemy;

public enum Stance {
    DEFENSIVE(25f),
    PASSIVE(30f),
    AGGRESSIVE(40f);

    public final float speed;
    Stance (float speed) {
        this.speed = speed;
    }

}
