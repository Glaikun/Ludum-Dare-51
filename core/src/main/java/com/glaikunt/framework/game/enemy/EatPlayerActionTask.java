package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.PlayerComponent;

public class EatPlayerActionTask extends AbstractLeafTask {

//    private final PlayerComponent playerBC;

    protected EatPlayerActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);

        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
//        this.playerBC = eac.getPlayerEntity().getComponent(PlayerComponent.class);
    }

    //TODO some funny/silly effect
    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }
}
