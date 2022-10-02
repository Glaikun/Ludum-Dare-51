package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TargetsComponent implements Component {

    private Vector2 targetHeatSource = new Vector2();

    public Vector2 getTargetHeatSource() {
        return targetHeatSource;
    }

    public void setTargetHeatSource(Vector2 targetHeatSource) {
        this.targetHeatSource.set(targetHeatSource);
    }
}
