package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;

public class AttackPlayerHoldingGroundActionTask extends AbstractLeafTask {
    public AttackPlayerHoldingGroundActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute AttackPlayerHoldingGroundActionTask"));
        System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
        return Status.SUCCEEDED;
    }
}