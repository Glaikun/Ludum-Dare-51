package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.Ansi;

public class HuntThePlayerDownActionTask extends AbstractLeafTask {
    public HuntThePlayerDownActionTask(Entity entity) {
        super(entity);
    }

    @Override
    public Status execute() {
        Gdx.app.log("DEBUG", Ansi.red("[AI] ")+Ansi.yellow("execute HuntThePlayerDownActionTask"));
        Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
        return Status.SUCCEEDED;
    }
}