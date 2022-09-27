package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.animation.MovementAnimationComponent;
import com.glaikunt.framework.application.ApplicationResources;

public class MovementAnimationSystem extends EntitySystem {

    private ApplicationResources applicationResources;

    private ImmutableArray<Entity> animationEntities;

    private ComponentMapper<MovementAnimationComponent> ac = ComponentMapper.getFor(MovementAnimationComponent.class);

    public MovementAnimationSystem(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.animationEntities = applicationResources.getEngine().getEntitiesFor(Family
                .all(MovementAnimationComponent.class)
                .get()
        );
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            MovementAnimationComponent movementAnimation = ac.get(entity);
            if (movementAnimation.isPaused()) {
                if (movementAnimation.getRotation() != 0) {
                    movementAnimation.setRotation(0);
                }
                return;
            }

            movementAnimation.getRotationTimer().tick(deltaTime);
            if (movementAnimation.getRotationTimer().isTimerEventReady()) {
                movementAnimation.setToggle(!movementAnimation.isToggle());
                movementAnimation.setRotation(0);
            }

            if (movementAnimation.isToggle()) {
                movementAnimation.setRotation(movementAnimation.getRotation() + (movementAnimation.getRotationSpeed() * deltaTime));
            } else {
                movementAnimation.setRotation(movementAnimation.getRotation() - (movementAnimation.getRotationSpeed() * deltaTime));
            }
        }
    }
}
