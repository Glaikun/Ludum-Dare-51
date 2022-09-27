package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

public class MassComponent implements Component {

    private float mass;

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
