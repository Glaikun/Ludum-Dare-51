package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.esc.component.common.HealthComponent;

import static com.glaikunt.framework.game.GameConstants.DEBUG;

/**
 * Used during the behaviourtree to track what's the target in one task and the subject in another
 */
public class TargetsComponent implements Component {

    private final Vector2 targetHeatSource = new Vector2();
    private final Vector2 targetPlayer = new Vector2();
    private Entity targetBreakable;

    public Vector2 getTargetHeatSource() {
        return targetHeatSource;
    }

    public void setTargetHeatSource(Vector2 targetHeatSource) {
        this.targetHeatSource.set(targetHeatSource);
    }

    public Vector2 getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Vector2 targetPlayer) {
        this.targetPlayer.set(targetPlayer);
    }

    public Entity getTargetBreakable() {
        return targetBreakable;
    }

    public void setTargetBreakable(Entity targetBreakable) {
        if (targetBreakable != null) {
            if (targetBreakable.getComponent(HealthComponent.class) != null) {
                this.targetBreakable = targetBreakable;
            } else {
                Gdx.app.debug(DEBUG, "[TargetsComponent] Unexpected: targetBreakable has no HealthComponent?!");
            }
        }
    }

    @Override
    public String toString() {
        return "TargetsComponent{" +
                "targetHeatSource=" + targetHeatSource +
                ", targetPlayer=" + targetPlayer +
                ", targetBreakable=" + targetBreakable +
                '}';
    }
}
