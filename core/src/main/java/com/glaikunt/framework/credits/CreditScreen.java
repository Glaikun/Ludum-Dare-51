package com.glaikunt.framework.credits;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.esc.component.misc.BloatingComponent;
import com.glaikunt.framework.esc.system.BloatingSystem;

import java.util.ArrayList;
import java.util.List;

public class CreditScreen extends Screen {

    private TickTimer textTimer;
    private List<CreditTextActor> credits = new ArrayList<>();
    private Entity endScreenEntity;
    private BloatingComponent bloating;
    private Vector2 size;
    private Texture heart;
    private float alpha = 0;

    private TickTimer endTimer = new TickTimer(12);

    public CreditScreen(ApplicationResources applicationResources) {
        super(applicationResources, Scaling.stretch, Scaling.stretch);
    }

    @Override
    public void show() {

        this.endScreenEntity = new Entity();
        this.bloating = new BloatingComponent();
        this.bloating.setMaxBloating(5);
        this.bloating.setSpeed(10f);
        this.endScreenEntity.add(bloating);
        getApplicationResources().getEngine().addEntity(endScreenEntity);
        getApplicationResources().getEngine().addSystem(new BloatingSystem(getEngine()));

        this.heart = getApplicationResources().getCacheRetriever().geTextureCache(null);
        this.size = new Vector2(heart.getWidth() * 8, heart.getHeight() * 8);

        this.textTimer = new TickTimer(3);
        this.textTimer.setTick(textTimer.getTargetTime());

        credits.add(new CreditTextActor(getApplicationResources(), "Programmer - Glaikunt"));
        credits.add(new CreditTextActor(getApplicationResources(), "Art - Glaikunt"));
        credits.add(new CreditTextActor(getApplicationResources(), "Music - KennyNL"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - Benboncan"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - Rudmer"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - Jammaj"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - Miss Burusdeer"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - InspectorJ"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - TRNGLE"));
        credits.add(new CreditTextActor(getApplicationResources(), "Sounds - Msantoro11"));

        credits.add(new CreditTextActor(getApplicationResources(), "   ---   "));

        credits.add(new CreditTextActor(getApplicationResources(), "Thank You"));

        String message = "Thanks For Playing!  \n\n";
        getFront().addActor(new TopTextActor(getApplicationResources(), message));
//        getApplicationResources().getAudioManager().loopGameOver();
    }

    @Override
    public void hide() {
        super.hide();

//        getApplicationResources().getAudioManager().stopGameOver();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void update(float delta) {

        if (textTimer.isTimerEventReady() && !credits.isEmpty()) {
            getFront().addActor(credits.get(0));
            credits.remove(0);
        }

        textTimer.tick(delta);
        getBackground().act();
        getFront().act();
        getUX().act();

    }

    @Override
    public void render2D() {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getFront().draw();
        if (credits.isEmpty()) {

//            if (alpha < 1) {
//                alpha += .2f * delta;
//            } else if (alpha > 1) {
//                alpha = 1;
//            }

            getFront().getBatch().begin();
            getFront().getBatch().setColor(1, 1, 1, alpha);
            getFront().getBatch().draw(heart, ((Display2D.WORLD_WIDTH / 2) - (size.x / 2f)) - (bloating.getBloating() / 2), ((Display2D.WORLD_HEIGHT / 2) - (size.y / 2f)) - (bloating.getBloating() / 2), size.x + (bloating.getBloating() / 2), size.y + (bloating.getBloating() / 2));
            getFront().getBatch().setColor(1, 1, 1, 1);
            getFront().getBatch().end();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}