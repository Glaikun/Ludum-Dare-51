package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class HealthComponent implements Component {

    private float maxHealth;
    private float health;

    private long lastDamagedNanotime = 0;

    /**
     * Initialises a very very weak health component by default
     */
    public HealthComponent() {
        this.maxHealth = 1f;
        this.health = 1f;
    }

    public HealthComponent(float startingHealth, float maxHealth) {
        this.maxHealth = maxHealth;
        this.health = startingHealth;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }


    public int getHealthPercent() {
        return (int)((health * 100)/maxHealth);
    }

    public boolean isExpired() {
        return health <= 0;
    }

    public HealthComponent setHealth(float health) {
        if (health < this.health) {
            lastDamagedNanotime = TimeUtils.nanoTime();
        }
        this.health = health;
        return this;
    }

    public void damage(float amount) {
        setHealth(getHealth()-amount);
    }

    public HealthComponent setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public boolean isRecentlyDamaged(long withinMillis) {
        if (lastDamagedNanotime == 0) return false;
        return TimeUtils.nanosToMillis(TimeUtils.timeSinceNanos(lastDamagedNanotime)) < withinMillis;
    }
}