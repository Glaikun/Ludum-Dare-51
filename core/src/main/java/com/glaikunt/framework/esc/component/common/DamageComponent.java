package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

/**
 * Typically the damage dealt. Likely passed to healthcomponent.damage(amount)
 */
public class DamageComponent implements Component {

    private float damage;

    public DamageComponent(float value) {
        this.damage = value;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
