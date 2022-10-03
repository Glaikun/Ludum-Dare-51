package com.glaikunt.framework.application3d;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.glaikunt.framework.DynamicDisplay;
import com.glaikunt.framework.application3d.scene.Stage;
import com.glaikunt.framework.cache.CacheRetriever;

import java.util.Arrays;
import java.util.List;

import static com.glaikunt.framework.Display2D.WORLD_HEIGHT;
import static com.glaikunt.framework.Display2D.WORLD_WIDTH;

public abstract class Screen implements com.badlogic.gdx.Screen {

    private ApplicationResources applicationResources;
    private Stage main, secondary;
    private com.badlogic.gdx.scenes.scene2d.Stage ux;

//    private final GLProfiler glProfiler;
//    private final DebugLabels debugLabels;

    protected Screen(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.main = new Stage(40, WORLD_WIDTH, WORLD_HEIGHT);
        this.secondary = new Stage(40, WORLD_WIDTH, WORLD_HEIGHT);

        this.ux = new com.badlogic.gdx.scenes.scene2d.Stage(new ScalingViewport(Scaling.stretch, WORLD_WIDTH, WORLD_HEIGHT));
        ((OrthographicCamera) this.ux.getCamera()).setToOrtho(false);
        this.ux.setDebugAll(false);

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
        getMain().resize(width, height);
        getUX().getViewport().update(width, height, true);
        getMain().getCamera().update();
        getUX().getCamera().update();

        Gdx.app.log(logDEBUG(), "Width: " + Gdx.graphics.getWidth() );
        Gdx.app.log(logDEBUG(), "Height: " + Gdx.graphics.getHeight() );
        Gdx.app.log(logDEBUG(), "worldWidth: " + WORLD_WIDTH );
        Gdx.app.log(logDEBUG(), "worldHeight: " + WORLD_HEIGHT );
    }

    @Override
    public void render(float delta) {
        if (!((DynamicDisplay) getDisplay()).isPaused()) {
            update(delta);
        }
        render2D(delta);

//        if (Gdx.app.getLogLevel() != Logger.NONE) {
//            debugLabels.update(glProfiler);
//            glProfiler.reset();
//        }
    }

//    public DebugLabels getDebugLabels() {
//        return debugLabels;
//    }

    public abstract void update(float delta);

    public abstract void render2D(float delta);

    protected String logDEBUG() {
        return "DEBUG";
    }

    @Override
    public void hide() {

        getEngine().removeAllEntities();
        getMain().clear();
        for (Actor actor : getUX().getActors()) {
            actor.clear();
        }
        getUX().clear();
    }

    @Override
    public void dispose() {

        getMain().dispose();
        getUX().dispose();
        getEngine().removeAllEntities();
    }

    public com.badlogic.gdx.scenes.scene2d.Stage getUX() {
        return ux;
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

    public Stage getMain() {
        return main;
    }

    public Stage getSecondary() {
        return secondary;
    }

    public List<PerspectiveCamera> getCameras() {
        return Arrays.asList((PerspectiveCamera) main.getCamera(),(PerspectiveCamera) secondary.getCamera());
    }

    public List<Stage> getStages() {
        return Arrays.asList(main, secondary);
    }
}
