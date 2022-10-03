package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {

    private int health = 1;
    private boolean hit, dead;
    private int deathFrom;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getDeathFrom() {
        return deathFrom;
    }

    public void setDeathFrom(int deathFrom) {
        this.deathFrom = deathFrom;
    }
}
