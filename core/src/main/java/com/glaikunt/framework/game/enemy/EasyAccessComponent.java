package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.game.map.levels.AbstractLevel;

public class EasyAccessComponent implements Component {
    private AbstractLevel currentAbstractLevel;
    private Entity playerEntity;

    public EasyAccessComponent(AbstractLevel currentAbstractLevel, Entity playerEntity) {
        this.currentAbstractLevel = currentAbstractLevel;
        this.playerEntity = playerEntity;
    }

    public AbstractLevel getCurrentLevel() {
        return currentAbstractLevel;
    }

    public void setCurrentLevel(AbstractLevel currentAbstractLevel) {
        this.currentAbstractLevel = currentAbstractLevel;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }
}
