package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public abstract class AbstractLeafTask extends LeafTask<Entity> {

    private final Entity entity;

    protected AbstractLeafTask(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }


    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return null;
    }
}
