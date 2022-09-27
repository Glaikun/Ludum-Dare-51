package com.glaikunt.framework;

import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.game.GameScreen3D;
import com.glaikunt.framework.application3d.ApplicationResources;

import static com.badlogic.gdx.utils.Logger.DEBUG;

public class Display3D extends Display {

	public static final float WORLD_WIDTH = 1280; //640 //1980 //320
	public static final float WORLD_HEIGHT = 960; //480 //1080 //240

	private ApplicationResources applicationResources;

	private boolean paused;

	@Override
	public void create () {

		Gdx.app.setLogLevel(DEBUG);

		this.applicationResources = new ApplicationResources(this);

		this.applicationResources.getCacheRetriever().loadCache();

		while(!applicationResources.getCacheRetriever().isCacheLoaded()) {
			this.applicationResources.getCacheRetriever().update();
		}
		getApplicationResources().getAudioManager().init(getApplicationResources().getCacheRetriever().getSoundCache());


		setScreen(new GameScreen3D(getApplicationResources()));
	}

	@Override
	public void render () {

		if (!isPaused()) {
			applicationResources.getEngine().update(Gdx.graphics.getDeltaTime());
			applicationResources.getAudioManager().update(Gdx.graphics.getDeltaTime());
		}

		super.render();
	}

	@Override
	public void dispose () {
		getApplicationResources().dispose();
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

	public ApplicationResources getApplicationResources() {
		return applicationResources;
	}
}
