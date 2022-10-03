package com.glaikunt.framework.game.map.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.cache.TextureCache;
import com.glaikunt.framework.esc.component.misc.FadeComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.esc.system.physics.BodyType;
import com.glaikunt.framework.game.player.PlayerActor;

import java.util.ArrayList;
import java.util.List;

public class LevelController extends CommonActor {

    private Stage front;
    private List<AbstractLevel> levels = new ArrayList<>();
    private AbstractLevel currentLevel;
    private PlayerActor currentPlayer;
    private Texture pixel;

    private FadeComponent.Fade fade;

    private boolean startLevelTransition, resetLevel;
    private TickTimer resetLevelTimer = new TickTimer(2f);

    public LevelController(ApplicationResources applicationResources, Stage front) {
        super(applicationResources);

        this.front = front;
        this.pixel = applicationResources.getTexture(TextureCache.PIXEL);

        this.levels.add(new DebugLevel(applicationResources, front));
        this.levels.add(new NextDebugLevel(applicationResources, front));

        AbstractLevel abstractLevel = levels.get(0);
        this.currentLevel = abstractLevel;
        this.currentLevel.init();
        this.currentPlayer = currentLevel.getPlayer();
        this.levels.remove(0);

        FadeComponent fadeComponent = new FadeComponent();
        this.fade = new FadeComponent.Fade();
        this.fade.setFade(0);
//        this.fade.setSpeed(1);
        this.fade.setMaxFade(1);
        fadeComponent.addFade(fade);
        getEntity().add(fadeComponent);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (fade.isFadeOut() || fade.isFadeIn()) {
            batch.setColor(0, 0, 0, fade.getFade());
            batch.draw(pixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
    }

    @Override
    public void act(float delta) {

        levelTransitionUpdate();

        resetLevelUpdate(delta);

        if (fade.getFade() == 0 && fade.isFadeOut()) {
            startLevelTransition = false;
            resetLevel = false;
            currentPlayer.getPlayerInput().setDisableInputMovement(false);
            fade.setFadeOut(false);
        }
    }

    private void resetLevelUpdate(float delta) {
        if (!resetLevel && !startLevelTransition) {

            if (getPlayer().getPlayer().isDead()) {
                resetLevel = true;
            }
        }

        if (resetLevel && !fade.isFadeOut() && !fade.isFadeIn()) {
            resetLevelTimer.tick(delta);
            if (resetLevelTimer.isTimerEventReady()) {
                fade.setFadeIn(true);
            }
        }

        if (fade.getFade() >= 1 && resetLevel) {
            front.clear();
            getEngine().removeAllEntities();
            getEngine().addEntity(getEntity());
            getCurrentLevel().reset();

            getCurrentLevel().init();
            this.currentPlayer = currentLevel.getPlayer();
            fade.setFadeOut(true);
        }
    }

    private void levelTransitionUpdate() {
        if (!startLevelTransition && !resetLevel) {
            for (BodyComponent contract : currentPlayer.getBody().getContactsByBody().keySet()) {

                if (contract.getBodyType().equals(BodyType.CHECKPOINT)) {
                    startLevelTransition = true;
                    fade.setFadeIn(true);
                    currentPlayer.getPlayerInput().setDisableInputMovement(true);
                    currentPlayer.getPlayerInput().setWalkRight(true);
                }
            }

        }

        if (fade.getFade() >= 1 && startLevelTransition) {
            //FIXME THERE BE DRAGONS
            front.clear();
            getEngine().removeAllEntities();
            getEngine().addEntity(getEntity());


            AbstractLevel abstractLevel = levels.get(0);
            currentLevel = abstractLevel;
            currentLevel.init();
            currentPlayer = currentLevel.getPlayer();
            levels.remove(0);
            fade.setFadeOut(true);
            currentPlayer.getPlayerInput().setDisableInputMovement(true);
        }
    }

    public AbstractLevel getCurrentLevel() {
        return currentLevel;
    }

    public PlayerActor getPlayer() {
        return currentPlayer;
    }
}
