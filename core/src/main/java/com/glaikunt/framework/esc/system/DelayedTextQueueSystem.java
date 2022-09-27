package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.esc.component.text.DelayedTextComponent;
import com.glaikunt.framework.esc.component.text.TextComponent;
import com.glaikunt.framework.esc.component.text.TextQueueComponent;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;

public class DelayedTextQueueSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TextQueueComponent> tqcm = ComponentMapper.getFor(TextQueueComponent.class);
    private ComponentMapper<DelayedTextComponent> dtcm = ComponentMapper.getFor(DelayedTextComponent.class);

    public DelayedTextQueueSystem(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(TextQueueComponent.class, DelayedTextComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            TextQueueComponent queue = tqcm.get(entity);
            DelayedTextComponent text = dtcm.get(entity);

            if (queue.getQueue().isEmpty() || queue.isPaused()) continue;

            if (text == null) {
                TextComponent poll = queue.getQueue().poll();
                entity.add(poll);
                continue;
            }

            if ( text.isFinished()) {

                queue.getSwapDelay().tick(deltaTime);
                if ((queue.isEnableSkipText() && Gdx.input.isKeyJustPressed(ANY_KEY)) || queue.getSwapDelay().isTimerEventReady()) {
                    entity.add(queue.getQueue().poll());
                }
            }
        }
    }
}
