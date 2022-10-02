package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.component.common.WarmthComponent;

public class TooColdConditionTask extends AbstractLeafTask {
    private final WarmthComponent warmth;
    public TooColdConditionTask(Entity entity) {
        super(entity);
        this.warmth = entity.getComponent(WarmthComponent.class);
    }

    @Override
    public Status execute() {
        Gdx.app.log("DEBUG", Ansi.red("[AI] ")+Ansi.yellow("execute TooColdConditionTask"));
        if (!warmth.isFrozen() && warmth.getWarmthFloat() > 0.5f) {
            Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.FAILED"));
            return Status.FAILED;
        }
    }
}
