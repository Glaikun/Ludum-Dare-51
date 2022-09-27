package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.animation.ModelAnimationComponent;

public class ModelAnimationSystem extends EntitySystem {

    private ImmutableArray<Entity> animationEntities;

    private ComponentMapper<ModelAnimationComponent> ac = ComponentMapper.getFor(ModelAnimationComponent.class);

    public ModelAnimationSystem(Engine engine) {
        animationEntities = engine.getEntitiesFor(Family.all(ModelAnimationComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            ModelAnimationComponent a = ac.get(entity);
            if (!a.isPlaying()) continue;
            a.setStateTime(a.getStateTime() + deltaTime);
            a.setCurrentFrame(a.getCurrentAnimation().getKeyFrame(a.getStateTime()));
        }

    }
}
