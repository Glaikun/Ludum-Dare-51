package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.AccelerationComponent;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PlayerComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class AttackPlayerActionTask extends AbstractLeafTask {

    private static final float LATERAL_ACCELERATION = 150f;

    private final BodyComponent playerBC;
    private final AccelerationComponent playerAccel;
    private final VelocityComponent playerVel;
    private final PlayerComponent player;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final EnemyInputComponent ic;

    public AttackPlayerActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.playerAccel = eac.getPlayerEntity().getComponent(AccelerationComponent.class);
        this.playerVel = eac.getPlayerEntity().getComponent(VelocityComponent.class);
        this.player = eac.getPlayerEntity().getComponent(PlayerComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute AttackPlayerActionTask"));

        if (bc.isContactedWithPlayer()) {

            ContactComponent playerContact = bc.getPlayerContact();

            if (playerContact.getNormal().x >= 1) {
                playerVel.x += LATERAL_ACCELERATION;
//                playerVel.y += LATERAL_ACCELERATION;
                player.setHealth(player.getHealth()-1);
                if (player.getHealth() <= 0) {
                    player.setDeathFrom(-1);
                }
            } else  if (playerContact.getNormal().x <= -1) {
                playerVel.x -= LATERAL_ACCELERATION;
//                playerVel.y += LATERAL_ACCELERATION;
                player.setHealth(player.getHealth()-1);
                if (player.getHealth() <= 0) {
                    player.setDeathFrom(1);
                }

            }
            player.setHealth(player.getHealth()-1);

            return Status.SUCCEEDED;
        } else {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}

