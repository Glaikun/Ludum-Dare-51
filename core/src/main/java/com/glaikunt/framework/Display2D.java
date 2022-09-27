package com.glaikunt.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.glaikunt.framework.splash.SplashScreen;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.utils.Logger.DEBUG;

public class Display2D extends Display {

	public static final float WORLD_WIDTH = 1280; //640 //1980 //320
	public static final float WORLD_HEIGHT = 960; //480 //1080 //240
	public static final String VERSION = "v0.1.0";

    private ApplicationResources applicationResources;

	private boolean paused, transitioning;

	@Override
	public void create () {

		Gdx.app.setLogLevel(DEBUG);

		this.applicationResources = new ApplicationResources(this);

		setScreen(new SplashScreen(getApplicationResources()));
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

	private void changeScreen(final Screen screen) {
		if (transitioning) return;
		transitioning = true;

		SequenceAction sequenceAction_1 = new SequenceAction();
		sequenceAction_1.addAction(fadeOut(1.5f));
		sequenceAction_1.addAction(run(new Runnable() {
			@Override
			public void run() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						setScreen(screen);

						for (Stage stage : screen.getStages()) {

							stage.getRoot().getColor().a = 0;
							stage.getRoot().addAction(fadeIn(1.5f));
						}
						transitioning = false;
					}
				});

			}
		}));
		for (Stage stage : ((Screen) getScreen()).getStages()) {

			if (((Screen) getScreen()).getStages().indexOf(stage) == 0) {
				stage.getRoot().getColor().a = 1;
				stage.getRoot().addAction(sequenceAction_1);
			} else {
				stage.getRoot().getColor().a = 1;
				stage.getRoot().addAction(fadeOut(1.5f));
			}
		}
	}
}
