package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;

public class PlayerInputSystem extends EntitySystem {

    private final ImmutableArray<Entity> animationEntities;

    private final ComponentMapper<AnimationComponent> ac = ComponentMapper.getFor(AnimationComponent.class);
    private final ComponentMapper<VelocityComponent> vc = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<PlayerInputComponent> pic = ComponentMapper.getFor(PlayerInputComponent.class);

    //TODO Jumping State

    public PlayerInputSystem(Engine engine) {
        animationEntities = engine.getEntitiesFor(
                Family
                        .one(PlayerInputComponent.class)
                        .all(AnimationComponent.class, VelocityComponent.class).get()
        );
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            AnimationComponent playerAnimation = ac.get(entity);
            VelocityComponent vel = vc.get(entity);
            AbstractPlayerInputComponent input = pic.get(entity);

            if (input.isMovingLeft()) {
//                pos.x -= speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.LEFT);
            }
            if (input.isMovingRight()) {
//                pos.x += speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.RIGHT);
            }


//            if (input.isMovingUp()) {
//                pos.y += speed;
//                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
//                input.setFacing(AbstractPlayerInputComponent.Direction.UP);
//            }
//            if (input.isMovingDown()) {
//                pos.y -= speed;
//                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
//                input.setFacing(AbstractPlayerInputComponent.Direction.DOWN);
//            }

//            playerAnimation.setCurrentAnimationId(getTextureKey(input.getAnimation(), input.getFacing()));
        }
    }

    private int getTextureKey(AbstractPlayerInputComponent.Animation animation, AbstractPlayerInputComponent.Direction direction) {
        return animation.ordinal() * AbstractPlayerInputComponent.Direction.values().length + direction.ordinal();
    }
}
