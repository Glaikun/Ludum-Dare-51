package com.glaikunt.framework.game.map.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.game.map.BreakableActor;
import com.glaikunt.framework.game.map.HeatSourceActor;
import com.glaikunt.framework.game.player.PlayerActor;

public abstract class AbstractLevel extends CommonActor {

    private final Stage front;

    public AbstractLevel(ApplicationResources applicationResources, Stage front) {
        super(applicationResources);
        this.front = front;
    }

    public void drawBackground() {

    }

    public void drawForeground() {

    }

    public void act(Stage stage) {

    }

    abstract PlayerActor getPlayer();

    public abstract Array<HeatSourceActor> getHeatSources();

    abstract void init();

    public Stage getFront() {
        return front;
    }

    public abstract Array<BreakableActor> getBreakables();

    public abstract void removeBreakable(Entity entity);
}
