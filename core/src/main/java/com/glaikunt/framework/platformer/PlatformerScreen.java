package com.glaikunt.framework.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Scaling;
import com.glaikunt.framework.platformer.misc.AppleActor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.esc.system.physics.GravitySystem;
import com.glaikunt.framework.platformer.misc.FloorActor;

public class PlatformerScreen extends Screen {

    public PlatformerScreen(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.stretch, Scaling.stretch);
    }

    @Override
    public void show() {

        getFront().addActor(new AppleActor(getApplicationResources()));
        getFront().addActor(new FloorActor(getApplicationResources()));

        getApplicationResources().getEngine().addSystem(new GravitySystem(getEngine()));
    }

    @Override
    public void update(float delta) {

        getBackground().act(delta);
        getFront().act(delta);
        getUX().act(delta);
    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
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
