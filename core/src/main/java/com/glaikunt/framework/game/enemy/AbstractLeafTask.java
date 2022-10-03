package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.glaikunt.framework.application.ApplicationResources;

public abstract class AbstractLeafTask extends LeafTask<Entity> {

    private final ApplicationResources applicationResources;
    private final Entity entity;

    protected AbstractLeafTask(Entity entity, ApplicationResources applicationResources) {
        this.entity = entity;
        this.applicationResources = applicationResources;
    }

    public Entity getEntity() {
        return entity;
    }

    public ApplicationResources getApplicationResources() {
        return applicationResources;
    }
    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return null;
    }
}
