package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

import static com.glaikunt.framework.game.GameConstants.DEBUG;

public class WarmthComponent implements Component {

    public static final float WARMTH_MAX = 100f;
    public static final float WARMTH_THRESHOLD = 0f;
    public static final float WARMTH_MIN = -1f;

    private float warmth;
    private boolean outside;
    private boolean nearHeatSource;

    public WarmthComponent(float warmth) {
        this.warmth = warmth;
    }

    public float getWarmth() {
        return warmth;
    }

    public float getWarmthFloat() {
        return warmth / WARMTH_MAX;
    }
    public boolean isFrozen() {
        return warmth < WARMTH_THRESHOLD;
    }

    public void setWarmth(float warmth) {
        if (warmth-this.warmth > 10f) {
            Gdx.app.debug(DEBUG, "Big delta in warmth jump?");
        }
        this.warmth = warmth;
        if (this.warmth > WARMTH_MAX) {
            this.warmth = WARMTH_MAX;
        } else if (this.warmth < WARMTH_MIN) {
            this.warmth = WARMTH_MIN;
        }
    }

    public boolean isNearHeatSource() {
        return nearHeatSource;
    }

    public boolean isOutside() {
        return outside;
    }

    public void setNearHeatSource(boolean nearHeatSource) {
        this.nearHeatSource = nearHeatSource;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }

    @Override
    public String toString() {
        return "WarmthComponent{" +
                "warmth=" + warmth +
                ", outside=" + outside +
                ", nearHeatSource=" + nearHeatSource +
                '}';
    }
}
