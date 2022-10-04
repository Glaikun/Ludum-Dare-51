package com.glaikunt.framework.application;

import com.badlogic.gdx.math.Vector2;

public final class GameUtils {

    private static final Vector2 tmp = new Vector2();
    private static final Vector2 vecCurv1 = new Vector2();
    private static final Vector2 vecCurv2 = new Vector2();

    private GameUtils() {
    }

    /**
     * Using Pythagoras Theorem to move towards target and same speed
     * @param currentPos
     * @param targetPos
     * @param speed
     * @return
     */
    public static Vector2 moveToTarget(Vector2 currentPos, Vector2 targetPos, float speed) {
        float startX = currentPos.x;
        float startY = currentPos.y;

        float endX = targetPos.x;
        float endY = targetPos.y;

        // On starting movement
        float distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        float directionX = (endX - startX) / distance;
        float directionY = (endY - startY) / distance;

        return tmp.set(directionX * speed, directionY * speed);
    }

    /**
     * Bezier Curves to target position
     * @param startPos
     * @param heightPos
     * @param targetPos
     * @param speed
     * @return
     */
    public static Vector2 curveToTarget(Vector2 startPos, Vector2 heightPos, Vector2 targetPos, float speed) {

        vecCurv1.set(startPos.lerp(heightPos, speed));
        vecCurv2.set(heightPos.lerp(targetPos, speed));
        return tmp.set(vecCurv1.lerp(vecCurv2, speed));
    }

    /**
     * Cap size between min & max with given value
     * @param min
     * @param max
     * @param value
     * @return
     */
    public static float clamp(float min, float max, float value) {

        return Math.min(max, Math.max(value, min));
    }

    /**
     * Get percentage based on size val
     *
     * @param currentVal
     * @param size
     * @param max
     * @return
     */
    public static float percentage(float currentVal, float size, float max) {

        return GameUtils.clamp(0, size, (currentVal * size) / max);
    }
}
