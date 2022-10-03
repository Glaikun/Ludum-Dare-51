package com.glaikunt.framework.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.glaikunt.framework.application3d.ApplicationResources;
import com.glaikunt.framework.application3d.Screen;

public class GameScreen3D extends Screen {

    public GameScreen3D(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

//        CoordinateActor actor = new CoordinateActor(getApplicationResources(), (PerspectiveCamera) getSecondary().getCamera());
//        getMain().addActor(actor);
//        getMain().enableCameraMovement(false);

//        getUX().addActor(new Image(getApplicationResources().getTexture(TextureCache.SOMETHING)));
    }

    @Override
    public void update(float delta) {

//        getDebugLabels().getDebugPlayerLabel().setText(
//                        "Px: " + getMain().getCamera().position.x +
//                        " Py: " + getMain().getCamera().position.y +
//                        " Pz: " + getMain().getCamera().position.z
//        );

        getMain().act(delta);
        getSecondary().act(delta);
        getUX().act(delta);
    }

    @Override
    public void render2D(float delta) {

//        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        getMain().draw();
        getSecondary().draw();
        getUX().draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
