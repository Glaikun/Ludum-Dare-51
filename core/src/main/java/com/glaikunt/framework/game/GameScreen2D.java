package com.glaikunt.framework.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.glaikunt.framework.FrameworkConstants;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.esc.component.camera.CameraControlsComponent;
import com.glaikunt.framework.esc.component.common.GravityComponent;
import com.glaikunt.framework.esc.system.CameraControlsSystem;
import com.glaikunt.framework.esc.system.PlayerInputSystem;
import com.glaikunt.framework.esc.system.physics.AccelDecaySystem;
import com.glaikunt.framework.esc.system.physics.CollisionListenerSystem;
import com.glaikunt.framework.esc.system.physics.GravitySystem;
import com.glaikunt.framework.esc.system.physics.PositionIterationsSystem;
import com.glaikunt.framework.esc.system.physics.VelocityIterationsSystem;
import com.glaikunt.framework.game.map.DebugLevel;

public class GameScreen2D extends Screen {

    public GameScreen2D(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.none, Scaling.stretch);
    }

    @Override
    public void update(float delta) {
//        {
//            Entity e = getApplicationResources().getEngine().getEntitiesFor(
//                    Family
//                            .one(PlayerInputComponent.class)
//                            .all(AnimationComponent.class, VelocityComponent.class).get()
//            ).get(0);
//            getDebugLabels().getDebugPlayerLabel().setText("Ax: " + e.getComponent(AccelerationComponent.class).x+" Vx: " + e.getComponent(VelocityComponent.class).x);
//        }
        getBackground().act(delta);
        getFront().act(delta);
        getUX().act(delta);
    }

    @Override
    public void show() {

        GravityComponent gravityComponent = new GravityComponent();
        getApplicationResources().getGlobalEntity().add(gravityComponent);

        CameraControlsComponent cameraControls = new CameraControlsComponent();
        cameraControls.setEnableMovement(false);
        cameraControls.setEnableZoom(true);
        cameraControls.getCameras().add((OrthographicCamera) getFront().getCamera());
        getApplicationResources().getImmutableGameEntity().add(cameraControls);
        getEngine().addEntity(getApplicationResources().getImmutableGameEntity());

        getFront().addActor(new DebugLevel(getApplicationResources(), getFront()));

        // ########### Physics [Order Maters] ###########
        getEngine().addSystem(new GravitySystem(getEngine()));
        getEngine().addSystem(new VelocityIterationsSystem(getEngine()));
        getEngine().addSystem(new PlayerInputSystem(getEngine()));

        getEngine().addSystem(new CollisionListenerSystem(getEngine()));

        getEngine().addSystem(new PositionIterationsSystem(getEngine()));
        getEngine().addSystem(new AccelDecaySystem(getEngine()));
        // ########### Physics [Order Maters] ###########


        getEngine().addSystem(new CameraControlsSystem(getEngine()));
    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(FrameworkConstants.LIGHT_BLACK.r, FrameworkConstants.LIGHT_BLACK.g, FrameworkConstants.LIGHT_BLACK.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        getFront().draw();
        getUX().draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
