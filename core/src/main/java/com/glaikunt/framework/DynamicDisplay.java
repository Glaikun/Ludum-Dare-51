package com.glaikunt.framework;

import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.splash.SplashScreen;

import static com.badlogic.gdx.utils.Logger.DEBUG;
import static com.badlogic.gdx.utils.Logger.NONE;

public class DynamicDisplay extends Display {

    public static final float WORLD_WIDTH = 1280; //640 //1980 //320
    public static final float WORLD_HEIGHT = 960; //480 //1080 //240

    private ApplicationResources applicationResources2D;

    private boolean paused;

    @Override
    public void create() {

        Gdx.app.setLogLevel(NONE);

        initApplicationResource2D();
//        initApplicationResource3D();

        setScreen(new SplashScreen(getApplicationResources2D()));
//        setScreen(new Prototype3DScreen(getApplicationResources3D()));
    }

    private void initApplicationResource2D() {
        this.applicationResources2D = new ApplicationResources(this);
//        this.applicationResources2D.getCacheRetriever().loadCache();
//        while (!applicationResources2D.getCacheRetriever().isCacheLoaded()) {
//            this.applicationResources2D.getCacheRetriever().update();
//        }
//        getApplicationResources2D().getAudioManager().init(getApplicationResources2D().getCacheRetriever().getSoundCache());
    }

//    private void initApplicationResource3D() {
//        this.applicationResources3D = new ApplicationResources(this);
//        this.applicationResources3D.getCacheRetriever().loadCache();
//        while(!applicationResources3D.getCacheRetriever().isCacheLoaded()) {
//            this.applicationResources3D.getCacheRetriever().update();
//        }
//        getApplicationResources2D().getAudioManager().init(getApplicationResources3D().getCacheRetriever().getSoundCache());
//    }

    @Override
    public void render() {

//        if (!isPaused()) {
//            applicationResources2D.getEngine().update(Gdx.graphics.getDeltaTime());
//            applicationResources2D.getAudioManager().update(Gdx.graphics.getDeltaTime());
//
//            applicationResources3D.getEngine().update(Gdx.graphics.getDeltaTime());
//            applicationResources3D.getAudioManager().update(Gdx.graphics.getDeltaTime());
//        }

        super.render();
    }

    @Override
    public void dispose() {
        getApplicationResources2D().dispose();
//        getApplicationResources3D().dispose();
    }

    @Override
    public void pause() {
        super.pause();
        this.paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        this.paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public ApplicationResources getApplicationResources2D() {
        return applicationResources2D;
    }
}
