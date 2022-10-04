package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.glaikunt.framework.DynamicDisplay;
import com.glaikunt.framework.cache.CacheRetriever;
import com.glaikunt.framework.esc.component.movement.PlayerInputComponent;
import com.glaikunt.framework.game.GameConstants;

import java.util.Arrays;
import java.util.List;

import static com.glaikunt.framework.game.GameConstants.DEBUG;


public abstract class Screen implements com.badlogic.gdx.Screen {

    private final ApplicationResources applicationResources;
    private final Stage front;
    private final Stage background;
    private final Stage ux;
    private final Stage immutableBackground;

//    private final GLProfiler glProfiler;
//    private final DebugLabels debugLabels;

    private final TickTimer logTimer = new TickTimer(3);
    private float accum = 0;
    private int lastCount = 0;

    protected Screen(ApplicationResources applicationResources, Scaling dynamicStageScaling, Scaling fixedStageScaling) {
        this.applicationResources = applicationResources;
        if (dynamicStageScaling.equals(Scaling.none)) {
            this.front = new Stage(new ExtendViewport(DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
            this.background = new Stage(new ExtendViewport(DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
        } else {
            this.front = new Stage(new ScalingViewport(dynamicStageScaling, DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
            this.background = new Stage(new ScalingViewport(dynamicStageScaling, DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
        }

        if (fixedStageScaling.equals(Scaling.none)) {
            this.ux = new Stage(new ExtendViewport(DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
            this.immutableBackground = new Stage(new ExtendViewport(DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
        } else {
            this.ux = new Stage(new ScalingViewport(fixedStageScaling, DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
            this.immutableBackground = new Stage(new ScalingViewport(fixedStageScaling, DynamicDisplay.WORLD_WIDTH, DynamicDisplay.WORLD_HEIGHT));
        }

        ((OrthographicCamera) this.front.getCamera()).setToOrtho(false);
        this.front.setDebugAll(Gdx.app.getLogLevel() != Logger.NONE);

        ((OrthographicCamera) this.background.getCamera()).setToOrtho(false);
        this.background.setDebugAll(Gdx.app.getLogLevel() != Logger.NONE);

        ((OrthographicCamera) this.background.getCamera()).setToOrtho(false);
        this.immutableBackground.setDebugAll(Gdx.app.getLogLevel() != Logger.NONE);

        ((OrthographicCamera) this.ux.getCamera()).setToOrtho(false);
        this.ux.setDebugAll(Gdx.app.getLogLevel() != Logger.NONE);


//        if (Gdx.app.getLogLevel() != Logger.NONE) {
//            this.debugLabels = new DebugLabels();
//            this.glProfiler = new GLProfiler(Gdx.graphics);
//            this.glProfiler.enable();
//
//            getUX().addActor(debugLabels.getDebugPlayerLabel());
//            getUX().addActor(debugLabels.getDebugGCLabel());
//            getUX().addActor(debugLabels.getDebugProfilerLabel());
//            getUX().addActor(debugLabels.getVersionLabel());
//        } else {
//            this.debugLabels = null;
//            this.glProfiler = null;
//        }
    }

    @Override
    public void resize(int width, int height) {
        getFront().getViewport().update(width, height, true);
        getBackground().getViewport().update(width, height, true);
        getUX().getViewport().update(width, height, true);
        setupCamera();
        getFront().getCamera().update();
        getBackground().getCamera().update();
        getUX().getCamera().update();

        if (GameConstants.GDX_APP_DEBUG_LOGGING) Gdx.app.debug(DEBUG, "Width: " + Gdx.graphics.getWidth());
        if (GameConstants.GDX_APP_DEBUG_LOGGING) Gdx.app.debug(DEBUG, "Height: " + Gdx.graphics.getHeight());
    }

    public void setupCamera() {

    }

    @Override
    public void render(float delta) {

        if (getApplicationResources().isEnableBox2d()) {
            accumUpdate(delta);
        } else {
            dynamicDelta(delta);
        }

        getApplicationResources().getAudioManager().update(delta);
        render2D();

//        if (Gdx.app.getLogLevel() != Logger.NONE) {
//            debugLabels.update(glProfiler);
//            glProfiler.reset();
//        }
    }

    private void dynamicDelta(float delta) {
        if (applicationResources.getDisplay().isPaused()) {
            return;
        }

        getApplicationResources().getEngine().update(delta);
        update(delta);

        this.logTimer.tick(delta);
        if (logTimer.isTimerEventReady()) {
            if (GameConstants.GDX_APP_DEBUG_LOGGING) Gdx.app.debug(DEBUG,
                    "delta time [" + delta + "]"
                            + ", fps [" + Gdx.graphics.getFramesPerSecond() + "]"
            );
        }
    }

    private void accumUpdate(float delta) {
        if (applicationResources.getDisplay().isPaused()) {
            return;
        }

        accum += delta;
        int count = 0;

        while (accum >= GameConstants.TIME_STEP) {
            accum -= GameConstants.TIME_STEP;

            if (!((DynamicDisplay) getDisplay()).isPaused()) {

                getApplicationResources().getWorld().step(GameConstants.TIME_STEP, GameConstants.VELOCITY_ITERATIONS, GameConstants.POSITION_ITERATIONS);
                getApplicationResources().getEngine().update(GameConstants.TIME_STEP);
                update(GameConstants.TIME_STEP);
            }
            count++;
        }

        if (Gdx.input.getInputProcessor() != null && lastCount == 0 && count != 0) {
            ((PlayerInputComponent) Gdx.input.getInputProcessor()).resetStoreInputs();
        }
        if (Gdx.input.getInputProcessor() != null && count == 0) {
            ((PlayerInputComponent) Gdx.input.getInputProcessor()).storeInputs();
        }

        this.logTimer.tick(delta);
        if (logTimer.isTimerEventReady()) {
            if (GameConstants.GDX_APP_DEBUG_LOGGING) Gdx.app.debug(DEBUG,
                    "delta time [" + delta + "]"
                            + ", count [" + count + "]"
                            + ", step [" + GameConstants.TIME_STEP + "]"
                            + ", fps [" + Gdx.graphics.getFramesPerSecond() + "]"
            );
        }
        this.lastCount = count;
    }

    public abstract void update(float delta);

    public abstract void render2D();
    @Override
    public void hide() {

        getEngine().removeAllEntities();
        for (Actor actor : getFront().getActors()) {
            actor.clear();
        }
        getFront().clear();
        for (Actor actor : getBackground().getActors()) {
            actor.clear();
        }
        getBackground().clear();
        for (Actor actor : getUX().getActors()) {
            actor.clear();
        }
        getUX().clear();
    }

    @Override
    public void dispose() {

        getFront().dispose();
        getBackground().dispose();
        getUX().dispose();
        getEngine().removeAllEntities();
    }

    protected ApplicationResources getApplicationResources() {
        return applicationResources;
    }

    protected Engine getEngine() {
        return getApplicationResources().getEngine();
    }

    protected CacheRetriever getCacheRetriever() {
        return getApplicationResources().getCacheRetriever();
    }

    protected Game getDisplay() {
        return applicationResources.getDisplay();
    }

    public Stage getFront() {
        return front;
    }

    public Stage getBackground() {
        return background;
    }

    public Stage getUX() {
        return ux;
    }

    public Stage getImmutableBackground() {
        return immutableBackground;
    }

    public List<OrthographicCamera> getCameras() {
        return Arrays.asList((OrthographicCamera) front.getCamera(), (OrthographicCamera) background.getCamera());
    }

//    public DebugLabels getDebugLabels() {
//        return debugLabels;
//    }

    public List<Stage> getStages() {
        return Arrays.asList(front, background, ux, immutableBackground);
    }
}
