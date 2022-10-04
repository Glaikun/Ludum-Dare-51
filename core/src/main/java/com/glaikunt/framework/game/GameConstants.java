package com.glaikunt.framework.game;

public interface GameConstants {

    boolean BEHAVIOUR_LOGGING = false;
    boolean GDX_APP_DEBUG_LOGGING = false; // help to prevent string creation

    String DEBUG = "DEBUG";
    float TIME_STEP = 1/60f;
    int VELOCITY_ITERATIONS = 6;
    int POSITION_ITERATIONS = 2;

    float PPM = 16f;
    float ZOOM = .5f;
}
