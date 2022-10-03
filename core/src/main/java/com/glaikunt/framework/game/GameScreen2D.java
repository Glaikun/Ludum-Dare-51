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
import com.glaikunt.framework.esc.system.AnimationSystem;
import com.glaikunt.framework.esc.system.CameraControlsSystem;
import com.glaikunt.framework.esc.system.EnemyInputSystem;
import com.glaikunt.framework.esc.system.FadeSystem;
import com.glaikunt.framework.esc.system.PlayerInputSystem;
import com.glaikunt.framework.esc.system.WarmthSystem;
import com.glaikunt.framework.esc.system.physics.CollisionListenerSystem;
import com.glaikunt.framework.esc.system.physics.CollisionSystem;
import com.glaikunt.framework.esc.system.physics.GravitySystem;
import com.glaikunt.framework.esc.system.physics.PositionIterationsSystem;
import com.glaikunt.framework.esc.system.physics.VelocityDecaySystem;
import com.glaikunt.framework.esc.system.physics.VelocityIterationsSystem;
import com.glaikunt.framework.game.map.levels.LevelController;

public class GameScreen2D extends Screen {

    private LevelController levelController;

    public GameScreen2D(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.none, Scaling.stretch);
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

        this.levelController = new LevelController(getApplicationResources(), getFront(), getBackground());
        getUX().addActor(levelController);

        // ########### Physics [Order Maters] ###########
        getEngine().addSystem(new GravitySystem(getEngine()));
        getEngine().addSystem(new PlayerInputSystem(getEngine()));
        getEngine().addSystem(new EnemyInputSystem(getEngine()));
        getEngine().addSystem(new VelocityIterationsSystem(getEngine()));

        getEngine().addSystem(new CollisionListenerSystem(getEngine()));
        getEngine().addSystem(new CollisionSystem(getEngine()));
        getEngine().addSystem(new VelocityDecaySystem(getEngine()));

        getEngine().addSystem(new PositionIterationsSystem(getEngine()));
        // ########### Physics [Order Maters] ###########

        getEngine().addSystem(new CameraControlsSystem(getEngine()));
        getEngine().addSystem(new WarmthSystem(getEngine()));
        getEngine().addSystem(new AnimationSystem(getEngine()));
        getEngine().addSystem(new FadeSystem(getEngine()));
    }

    @Override
    public void setupCamera() {

        ((OrthographicCamera) this.getFront().getCamera()).zoom = GameConstants.ZOOM;
        getFront().getCamera().position.set(levelController.getPlayer().getX() + (levelController.getPlayer().getWidth() / 2), (levelController.getPlayer().getY()) + (levelController.getPlayer().getHeight()*2), 0);
    }

    @Override
    public void update(float delta) {
        getBackground().act(delta);
        getFront().act(delta);
        getUX().act(delta);
        levelController.getCurrentLevel().act(getFront());
    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(FrameworkConstants.DARK_BLUE.r, FrameworkConstants.DARK_BLUE.g, FrameworkConstants.DARK_BLUE.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        levelController.getCurrentLevel().drawBackground();
        levelController.getCurrentLevel().drawForeground();
        getFront().draw();
        getUX().draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
