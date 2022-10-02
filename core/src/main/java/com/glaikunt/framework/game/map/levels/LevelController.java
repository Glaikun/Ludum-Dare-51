package com.glaikunt.framework.game.map.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
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

    private boolean startTransition = false;

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

        if (!startTransition) {
            for (BodyComponent contract : currentPlayer.getBody().getContactsByBody().keySet()) {

                if (contract.getBodyType().equals(BodyType.CHECKPOINT)) {
                    startTransition = true;
                    fade.setFadeIn(true);
                    currentPlayer.getPlayerInput().setDisableInputMovement(true);
                    currentPlayer.getPlayerInput().setWalkRight(true);
                }
            }
        }

        if (fade.getFade() >= 1) {
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

        if (fade.getFade() == 0) {
            startTransition = false;
            currentPlayer.getPlayerInput().setDisableInputMovement(false);
            fade.setFadeOut(false);
        }
    }

    public AbstractLevel getCurrentLevel() {
        return currentLevel;
    }

    public PlayerActor getPlayer() {
        return currentPlayer;
    }
}
