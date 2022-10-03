package com.glaikunt.framework.game.enemy;

public enum Stance {
    DEFENSIVE(20f),
    PASSIVE(25f),
    AGGRESSIVE(40f);

    public final float speed;
    Stance (float speed) {
        this.speed = speed;
    }

}
