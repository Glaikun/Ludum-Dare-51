package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Typically the damage dealt. Likely passed to healthcomponent.damage(amount)
 */
public class DamageComponent implements Component {

    private float damage;
    private long lastDamagedNanotime = 0;

    public DamageComponent(float value) {
        this.damage = value;
    }

    public float getDamage() {
        return damage;
    }
    public float doDamage() {
        lastDamagedNanotime = System.nanoTime();
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public long getLastDamagedNanotime() {
        return lastDamagedNanotime;
    }
    public boolean isRecentlyIssuedDamaged(long withinMillis) {
        if (lastDamagedNanotime == 0) return false;
        return TimeUtils.nanosToMillis(TimeUtils.timeSinceNanos(lastDamagedNanotime)) < withinMillis;
    }
}
