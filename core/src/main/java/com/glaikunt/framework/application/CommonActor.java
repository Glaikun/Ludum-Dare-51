package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;

public abstract class CommonActor extends Actor {

    private final ApplicationResources applicationResources;

    protected PositionComponent pos;
    protected SizeComponent size;
    protected Entity entity;

    protected CommonActor(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.entity = new Entity();
        this.pos = new PositionComponent(0, 0);
        this.size = new SizeComponent(0, 0);
        getEntity().add(pos);
        getEntity().add(size);
        getEngine().addEntity(getEntity());
    }

    public ApplicationResources getApplicationResources() {
        return applicationResources;
    }

    @Override
    public float getX() {
        return pos.x;
    }

    @Override
    public float getY() {
        return pos.y;
    }

    @Override
    public float getWidth() {
        return size.x;
    }

    @Override
    public float getHeight() {
        return size.y;
    }

    public Entity getEntity() {
        return entity;
    }

    public Engine getEngine() {
        return getApplicationResources().getEngine();
    }

    @Override
    public boolean remove() {
        getApplicationResources().getEngine().removeEntity(getEntity());
        return super.remove();
    }
}
