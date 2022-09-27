package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;

public class BodyComponent implements Component {

    public static enum BODY_TYPE {
        DYNAMIC,
        KINEMATIC,
        STATIC
    }

    private BODY_TYPE bodyType;

    public BODY_TYPE getBodyType() {
        return bodyType;
    }

    public void setBodyType(BODY_TYPE bodyType) {
        this.bodyType = bodyType;
    }
}
