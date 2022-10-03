package com.glaikunt.framework.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.glaikunt.framework.FrameworkConstants;
import com.glaikunt.framework.esc.system.AnimationSystem;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.esc.system.FadeSystem;
import com.glaikunt.framework.game.GameScreen2D;

public class SplashScreen extends Screen {

    private LogoActor logo;
    private FadeActor fadeActor;

    private GameScreen2D gameScreen2D;

    public SplashScreen(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.stretch, Scaling.stretch);

        applicationResources.getCacheRetriever().loadCache();
    }

    @Override
    public void show() {

        this.logo = new LogoActor(getApplicationResources());
        getFront().addActor(logo);

        getEngine().addSystem(new AnimationSystem(getEngine()));
        getEngine().addSystem(new FadeSystem(getEngine()));
    }

    @Override
    public void update(float delta) {

        if (!getApplicationResources().getCacheRetriever().isCacheLoaded()) {
            getApplicationResources().getCacheRetriever().update();
        } else if (gameScreen2D == null) {
            getApplicationResources().getAudioManager().init(getApplicationResources().getCacheRetriever().getSoundCache());
            this.gameScreen2D = new GameScreen2D(getApplicationResources());
        }

        getApplicationResources().getUxStageMousePosition().set(getUX().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
        getBackground().act(delta);
        getFront().act(delta);
        getUX().act(delta);
    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(FrameworkConstants.WHITE.r, FrameworkConstants.WHITE.g, FrameworkConstants.WHITE.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        getFront().draw();
        getUX().draw();

        //Put in renderer due to flash of clear colour.
        if (logo.isComplete() && fadeActor == null) {
            this.fadeActor = new FadeActor(getApplicationResources());
            this.fadeActor.setColor(Color.BLACK);
            getUX().addActor(fadeActor);
        } else if (gameScreen2D != null && getApplicationResources().getCacheRetriever().isCacheLoaded() && fadeActor != null && fadeActor.isComplete()) {

            getDisplay().setScreen(gameScreen2D);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
