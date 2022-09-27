package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.input.SelectableComponent;

public class SelectableSystem extends EntitySystem {

    private final ApplicationResources applicationResources;
    private final ImmutableArray<Entity> entities;

    private final ComponentMapper<SelectableComponent> selectableCM = ComponentMapper.getFor(SelectableComponent.class);
    private final ComponentMapper<PositionComponent> positionCM = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<SizeComponent> sizeCM = ComponentMapper.getFor(SizeComponent.class);

    private final Rectangle tmp = new Rectangle();

    public SelectableSystem(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.entities = applicationResources.getEngine().getEntitiesFor(Family.all(SelectableComponent.class, PositionComponent.class, SizeComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            SelectableComponent selectable = selectableCM.get(entity);
            PositionComponent pos = positionCM.get(entity);
            SizeComponent size = sizeCM.get(entity);

            if (selectable.isDisabled()) {

                if (selectable.isJustPressed()) {
                    selectable.setJustPressed(false);
                }
                if (selectable.isHoveredOver()) {
                    selectable.setHoveredOver(false);
                }
                continue;
            }

            tmp.set(pos.x, pos.y, size.x, size.y);

            if (selectable.isJustPressed()) {
                selectable.setJustPressed(false);
            }

            if (selectable.isUiMousePositionCheck() && tmp.contains(applicationResources.getUxStageMousePosition().x, applicationResources.getUxStageMousePosition().y)) {

                if (Gdx.input.isButtonJustPressed(selectable.getInput())) {
                    selectable.setJustPressed(true);
                }
                selectable.setHoveredOver(true);
            } else if (selectable.isFrontMousePositionCheck() && tmp.contains(applicationResources.getFrontStageMousePosition().x, applicationResources.getFrontStageMousePosition().y)) {

                if (Gdx.input.isButtonJustPressed(selectable.getInput())) {
                    selectable.setJustPressed(true);
                }
                selectable.setHoveredOver(true);
            } else if (selectable.isHoveredOver()) {

                selectable.setHoveredOver(false);
            }
        }
    }
}
