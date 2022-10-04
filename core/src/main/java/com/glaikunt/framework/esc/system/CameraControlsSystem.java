package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.glaikunt.framework.esc.component.camera.CameraControlsComponent;
import com.glaikunt.framework.application.GameUtils;

public class CameraControlsSystem extends EntitySystem {

    private final ImmutableArray<Entity> entities;

    private final ComponentMapper<CameraControlsComponent> cccm = ComponentMapper.getFor(CameraControlsComponent.class);

    public CameraControlsSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(CameraControlsComponent.class).get());
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            CameraControlsComponent as = cccm.get(entity);
            for (OrthographicCamera camera : as.getCameras()) {

                if (as.isEnableMovement()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
                        camera.position.x -= as.getMovementSpeed() * delta;

                    } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
                        camera.position.x += as.getMovementSpeed() * delta;
                    }

                    if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ) {
                        camera.position.y += as.getMovementSpeed() * delta;

                    } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
                        camera.position.y -= as.getMovementSpeed() * delta;
                    }
                }

                if (as.isEnableZoom()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                        camera.zoom = GameUtils.clamp(.01f, 3, camera.zoom + (as.getScrollSpeed() * delta));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                        camera.zoom = GameUtils.clamp(.01f, 3, camera.zoom + (-as.getScrollSpeed() * delta));
                    }
                }

            }
        }
    }
}
