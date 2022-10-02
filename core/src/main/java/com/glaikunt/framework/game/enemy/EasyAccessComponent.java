package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.game.map.Level;

public class EasyAccessComponent implements Component {
    private Level currentLevel;
    private Entity playerEntity;

    public EasyAccessComponent(Level currentLevel, Entity playerEntity) {
        this.currentLevel = currentLevel;
        this.playerEntity = playerEntity;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }
}
