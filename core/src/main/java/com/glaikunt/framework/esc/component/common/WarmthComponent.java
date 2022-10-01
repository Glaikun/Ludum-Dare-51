package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

public class WarmthComponent implements Component {

    public static final float WARMTH_MAX = 100f;
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

    public void setWarmth(float warmth) {
        this.warmth = warmth;
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
