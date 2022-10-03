package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.PlayerComponent;

public class PlayerDeadConditionTask extends AbstractLeafTask {

    private final PlayerComponent playerBC;

    protected PlayerDeadConditionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);

        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(PlayerComponent.class);
    }

    @Override
    public Status execute() {
        if (playerBC.isDead()) {
            return Status.SUCCEEDED;
        }

        return Status.FAILED;
    }
}
