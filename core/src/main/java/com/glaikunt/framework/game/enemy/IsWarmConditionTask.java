package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.WarmthComponent;

public class IsWarmConditionTask extends AbstractLeafTask {
    private final WarmthComponent warmth;
    public IsWarmConditionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        this.warmth = entity.getComponent(WarmthComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute IsWarmConditionTask [")+Ansi.green(warmth.toString())+Ansi.yellow("]"));
        if (!warmth.isFrozen() && warmth.getWarmthFloat() > 0.8f) {
            System.out.println( Ansi.red("  |- ")+Ansi.red("~~ TOASTY ~~"));
            System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}