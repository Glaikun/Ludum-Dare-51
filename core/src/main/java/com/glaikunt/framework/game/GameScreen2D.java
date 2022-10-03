package com.glaikunt.framework.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Scaling;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.VignettingEffect;
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

    private static final boolean isVFX = false;
    private  VfxManager vfxManager;
    private  VignettingEffect vfxVignettingEffect;
    private  BloomEffect vfxBloomEffect;

    public GameScreen2D(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.none, Scaling.stretch);

        if (isVFX) {
            vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

            // Create and add an effect.
            // VfxEffect derivative classes serve as controllers for the effects.
            // They provide public properties to configure and control them.

            vfxVignettingEffect = new VignettingEffect(false);
//        vfxBloomEffect = new BloomEffect();
            vfxManager.addEffect(vfxVignettingEffect);
//        vfxManager.addEffect(vfxBloomEffect);
        }
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
        levelController.getBlizzard().updatePosition(getFront().getCamera().position.x, getFront().getCamera().position.y);
        levelController.getFogActor().updatePosition(getFront().getCamera().position.x, getFront().getCamera().position.y);
        levelController.getFogActor2().updatePosition(getFront().getCamera().position.x, getFront().getCamera().position.y);
        levelController.getCurrentLevel().act(getFront());
        getBackground().act(delta);
        getFront().act(delta);
        getUX().act(delta);
    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(FrameworkConstants.DARK_BLUE.r, FrameworkConstants.DARK_BLUE.g, FrameworkConstants.DARK_BLUE.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (isVFX) {
            // Clean up internal buffers, as we don't need any information from the last render.
            vfxManager.cleanUpBuffers();

            // Begin render to an off-screen buffer.
            vfxManager.beginInputCapture();
        }

        levelController.getCurrentLevel().drawBackground();
        getBackground().draw();
        levelController.getCurrentLevel().drawForeground();
        getFront().draw();
        getUX().draw();

        if (isVFX) {
            // End render to an off-screen buffer.
            vfxManager.endInputCapture();

            // Apply the effects chain to the captured frame.
            vfxManager.applyEffects();

            // Render result to the screen.
            vfxManager.renderToScreen();
        }

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
        if (isVFX) {
            // Since VfxManager has internal frame buffers,
            // it implements Disposable interface and thus should be utilized properly.
            vfxManager.dispose();

            // *** PLEASE NOTE ***
            // VfxManager doesn't dispose attached VfxEffects.
            // This is your responsibility to manage their lifecycle.
            vfxVignettingEffect.dispose();
        }
    }
}
