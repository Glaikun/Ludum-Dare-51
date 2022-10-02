package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.Ansi;

public class PlayerNearbyConditionTask extends AbstractLeafTask {
    public PlayerNearbyConditionTask(Entity entity) {
        super(entity);
    }

    @Override
    public Status execute() {
        Gdx.app.log("DEBUG", Ansi.red("[AI] ")+Ansi.yellow("execute PlayerNearbyConditionTask"));
        Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
        return Status.SUCCEEDED;
    }
}
