package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;

import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.*;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;
import com.glaikunt.framework.game.map.levels.AbstractLevel;

import java.util.Map;

public class EnemyActor extends CommonActor {

    private final AccelerationComponent acceleration;
    private final VelocityComponent velocity;
    private final AnimationComponent animation;

    private final WarmthComponent warmth;
    private final TargetsComponent targets;
    private final DamageComponent damage;
    private final EnemyInputComponent input;
    private final BodyComponent body;

    private final Vector2 tmpVector2 = new Vector2();

    private final BehaviorTree<Entity> behaviorTree;

    public EnemyActor(ApplicationResources applicationResources, Vector2 pos, AbstractLevel abstractLevel) {
        this(applicationResources, pos, abstractLevel, Stance.values()[MathUtils.random(Stance.values().length-1)]);
    }
    public EnemyActor(ApplicationResources applicationResources, Vector2 pos, AbstractLevel abstractLevel, Stance stance) {
        super(applicationResources);

        this.acceleration = new AccelerationComponent();
        this.velocity = new VelocityComponent();
        this.animation = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.ENEMY), 1, 1);
        this.warmth = new WarmthComponent(WarmthComponent.WARMTH_MAX);
        this.targets = new TargetsComponent();
        this.damage = new DamageComponent(10f);

        this.input = new EnemyInputComponent();

        this.pos.set(pos);
        this.size.set(animation.getCurrentFrame().getRegionWidth()-1, animation.getCurrentFrame().getRegionHeight()-1);

        this.body = new BodyComponent();
        this.body.setBodyType(BodyType.ENEMY);
        this.body.set(getX()+1, getY()+1, getWidth()-2, getHeight()-2);

        getEntity().add(acceleration);
        getEntity().add(velocity);
        getEntity().add(animation);
        getEntity().add(warmth);
        getEntity().add(targets);
        getEntity().add(damage);
        getEntity().add(input);
        getEntity().add(body);
        getEntity().add(getApplicationResources().getGlobalEntity().getComponent(GravityComponent.class));
        final ImmutableArray<Entity> playerEntities = applicationResources.getEngine().getEntitiesFor(Family.all(PlayerInputComponent.class).get());
        getEntity().add(new EasyAccessComponent(abstractLevel, playerEntities.get(0)));
        this.behaviorTree = new BehaviorTree<>(BehaviourFactory.getBehaviour(stance, entity));
        this.behaviorTree.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(warmth.getWarmthFloat(), warmth.getWarmthFloat(), 1.0f, 1f);
        batch.draw(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
        batch.setColor(Color.WHITE);
    }

    @Override
    public void act(float delta) {

//        if (!Task.Status.SUCCEEDED.equals(behaviorTree.getStatus())) {
            System.out.println( "FrameId: "+Gdx.graphics.getFrameId()+" [E] behaviorTree.getStatus() "+behaviorTree.getStatus()+" behaviorTree.step()");
            behaviorTree.step();
//        }

        if (!getBody().getBeforeContacts().isEmpty()) {
            System.out.println( "[E] Before Collide Intersection: " + getBody().getBeforeContacts().size() + ", and body contacts is now: " + getBody().getContactsByBody().size());
//
//            int c = 0;
//            for (Map.Entry<BodyComponent, ContactComponent> entry : body.getContactsByBody().entrySet()) {
//                BodyComponent key = entry.getKey();
//                ContactComponent contact = entry.getValue();
//                System.out.println( "  |- [E] "+c+" "+key+" => "+contact);
//                        c++;
//            }
        }
        if (!getBody().getAfterContacts().isEmpty()) {
            System.out.println( "[E] After Collide Intersection: " + getBody().getAfterContacts().size() + ", and body contacts is now: " + getBody().getContactsByBody().size());
//            int c = 0;
//            for (Map.Entry<BodyComponent, ContactComponent> entry : body.getContactsByBody().entrySet()) {
//                BodyComponent key = entry.getKey();
//                ContactComponent contact = entry.getValue();
//                System.out.println( "  |- [E] "+c+" "+key+" => "+contact);
//                c++;
//            }
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.rect(getBodyRect().getX(), getBodyRect().getY(), getBodyRect().width, getBodyRect().height);
    }

    public BodyComponent getBody() {
        return body;
    }

    public Rectangle getBodyRect() {
        return body;
    }
}
