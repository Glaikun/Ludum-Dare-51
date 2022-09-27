package com.glaikunt.framework.game;

import com.badlogic.gdx.math.Vector2;

public interface GameConstants {

    Vector2 vec2 = new Vector2();
    Vector2 vec3 = new Vector2();

    float TIME_STEP = 1/60f;
    int VELOCITY_ITERATIONS = 6;
    int POSITION_ITERATIONS = 2;

    float PPM = 16f;
    float ZOOM = 1 / 4f;
}
