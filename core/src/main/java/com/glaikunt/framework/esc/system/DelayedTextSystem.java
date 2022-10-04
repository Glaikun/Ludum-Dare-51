package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.glaikunt.framework.esc.component.text.DelayedTextComponent;

public class DelayedTextSystem extends EntitySystem {


    private final ImmutableArray<Entity> entities;

    private final ComponentMapper<DelayedTextComponent> tcm = ComponentMapper.getFor(DelayedTextComponent.class);

    public DelayedTextSystem(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(DelayedTextComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            DelayedTextComponent text = tcm.get(entity);

            if (text.isPaused()) {
                continue;
            }

            text.getDelay().tick(deltaTime);

            if (text.isShake()) {
                text.getShakePos().set(MathUtils.random(-2.5f, 2.5f), MathUtils.random(-2.5f, 2.5f));
            }

            if (!text.getText().equals(text.getDeltaText()) && text.getDelay().isTimerEventReady()) {

                text.setDeltaText(text.getDeltaText() + text.getText().charAt(text.getDeltaText().length()));
                text.getLayout().setText(text.getFont(), text.getDeltaText(), text.getColour(), text.getTargetWidth(), text.getAlign(), text.isWrap());
            }

            if (!text.isFinished() && text.getText().equals(text.getDeltaText())) {
                text.setFinished(true);
            }
        }
    }
}
