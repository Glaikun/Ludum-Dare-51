package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.PlayerComponent;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;

public class LevelCompleteConditionTask extends AbstractLeafTask {

    private final PlayerComponent playerBC;
    private final EnemyInputComponent ic;

    protected LevelCompleteConditionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);

        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(PlayerComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        if (playerBC.isLevelComplete()) {
            ic.setLeft(false);
            ic.setRight(false);
            ic.setJump(false);
            return Status.SUCCEEDED;
        }

        return Status.FAILED;
    }
}
