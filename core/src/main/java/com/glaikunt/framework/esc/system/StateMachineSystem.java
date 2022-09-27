package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.statemachine.StateComponent;

public class StateMachineSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<StateComponent> esc = ComponentMapper.getFor(StateComponent.class);

    public StateMachineSystem(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(StateComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            StateComponent a = esc.get(entity);

            if (a.isPause()) {
                continue;
            }

            a.getStateMachine().act(deltaTime);
        }
    }
}
