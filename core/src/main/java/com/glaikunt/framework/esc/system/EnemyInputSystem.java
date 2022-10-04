package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.application.GameUtils;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.SpeedComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.common.WarmthComponent;
import com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class EnemyInputSystem extends EntitySystem {
    private static final float JUMPING_ACCELERATION = 75;
    private final ImmutableArray<Entity> animationEntities;

    private final ComponentMapper<AccelerationComponent> acm = ComponentMapper.getFor(AccelerationComponent.class);
    private final ComponentMapper<EnemyInputComponent> eic = ComponentMapper.getFor(EnemyInputComponent.class);
    private final ComponentMapper<BodyComponent> bcm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<WarmthComponent> wcm = ComponentMapper.getFor(WarmthComponent.class);
    private final ComponentMapper<SpeedComponent> sc = ComponentMapper.getFor(SpeedComponent.class);

    //TODO Jumping State

    public EnemyInputSystem(Engine engine) {
        animationEntities = engine.getEntitiesFor(
                Family
                        .one(EnemyInputComponent.class)
                        .all(AnimationComponent.class, VelocityComponent.class, BodyComponent.class).get()
        );
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            AccelerationComponent ac = acm.get(entity);
            AbstractPlayerInputComponent input = eic.get(entity);
            BodyComponent body = bcm.get(entity);
            WarmthComponent warmth = wcm.get(entity);
            SpeedComponent speed = sc.get(entity);

            if (input.isMovingLeft()) {
//                pos.x -= speed;
                ac.x = -speed.getSpeed();
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.LEFT);
            } else if (input.isMovingRight()) {
//                pos.x += speed;
                ac.x = speed.getSpeed();
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.RIGHT);
            } else {
                ac.x = 0;
                input.setAnimation(AbstractPlayerInputComponent.Animation.IDLE);
            }

            if (input.isJumping() && body.isContactedWithFloor()) {
//                pos.x += speed;
                if (warmth != null && !warmth.isFrozen()) {
                    ac.y = JUMPING_ACCELERATION * GameUtils.clamp(.7f, 1f, warmth.getWarmthFloat()*2);
                    input.setAnimation(AbstractPlayerInputComponent.Animation.JUMP);
                }
            }
        }
    }

    private int getTextureKey(AbstractPlayerInputComponent.Animation animation, AbstractPlayerInputComponent.Direction direction) {
        return animation.ordinal() * AbstractPlayerInputComponent.Direction.values().length + direction.ordinal();
    }
}
