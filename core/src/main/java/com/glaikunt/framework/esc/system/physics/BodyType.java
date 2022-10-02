package com.glaikunt.framework.esc.system.physics;

public enum BodyType {

    DYNAMIC,
    STATIC,
    CHECKPOINT,
    INDOORS, // don't hate me - I know this is better kept out of bodycomponent.contacts
    HEATSOURCE,
    ENEMY,
    PLAYER,
    BLOCK
}
