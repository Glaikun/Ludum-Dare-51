package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;

public class PlayerInputSystem extends EntitySystem {

//    IntMap<Texture> movementTextures = new IntMap<>();
//    movementTextures.put(getTextureKey(MOVEMENT, LEFT), applicationResources.getTexture(TextureCache.PLAYER_RUN_E));
//    this.playerAnimation = new AnimationComponent();
//    this.playerAnimation.setup(heavyAttackETextures, getTextureKey(HEAVY_ATTACK, RIGHT), 5, 1, new Vector2(-20, 0));

    private static final float INVERSE_ROOT_2_DIAGONAL_SPEED = 1f / (float) Math.sqrt(2);

    private final ImmutableArray<Entity> animationEntities;

    private final ComponentMapper<AnimationComponent> ac = ComponentMapper.getFor(AnimationComponent.class);
    private final ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> vc = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<PlayerInputComponent> pic = ComponentMapper.getFor(PlayerInputComponent.class);

    public PlayerInputSystem(Engine engine) {
        animationEntities = engine.getEntitiesFor(
                Family
                        .one(PlayerInputComponent.class)
                        .all(AnimationComponent.class, PositionComponent.class, VelocityComponent.class).get()
        );
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            AnimationComponent playerAnimation = ac.get(entity);
            PositionComponent pos = pc.get(entity);
            VelocityComponent vel = vc.get(entity);
            AbstractPlayerInputComponent input = pic.get(entity);

            if (input.getForceAnimation() != null) {

                if (input.getForceAnimation() != null) {
                    playerAnimation.setCurrentAnimationId(getTextureKey(input.getForceAnimation(), input.getFacing()));
                }

                if (input.isReset()) {
                    playerAnimation.reset();
                    playerAnimation.getCurrentAnimation().setPlayMode(Animation.PlayMode.NORMAL);
                    input.setReset(false);
                }

                if (playerAnimation.isAnimationFinished()) {
                    input.setForceAnimation(null);

                }
                continue;
            }

            float speed = 0;
            if (input.isSprinting()) {
                speed = vel.getSprintSpeed();
            }

            if (input.isMovingDiagonally()) {
                speed *= INVERSE_ROOT_2_DIAGONAL_SPEED;
            }

            speed *= delta;

            if (input.isMovingLeft()) {
                pos.x -= speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.LEFT);
            }
            if (input.isMovingRight()) {
                pos.x += speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.RIGHT);
            }
            if (input.isMovingUp()) {
                pos.y += speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.UP);
            }
            if (input.isMovingDown()) {
                pos.y -= speed;
                input.setAnimation(AbstractPlayerInputComponent.Animation.MOVEMENT);
                input.setFacing(AbstractPlayerInputComponent.Direction.DOWN);
            }

            playerAnimation.setCurrentAnimationId(getTextureKey(input.getAnimation(), input.getFacing()));
        }
    }

    private int getTextureKey(AbstractPlayerInputComponent.Animation animation, AbstractPlayerInputComponent.Direction direction) {
        return animation.ordinal() * AbstractPlayerInputComponent.Direction.values().length + direction.ordinal();
    }
}
