package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.ContactComponent;
import com.glaikunt.framework.esc.component.common.PlayerComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.movement.AbstractPlayerInputComponent;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.game.GameConstants;

public class AttackPlayerHoldingGroundActionTask extends AbstractLeafTask {

    private static final int RADIUS = 32; // tile
    private static final float LATERAL_ACCELERATION = 150f;

    private final VelocityComponent playerVel;
    private final PlayerComponent player;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final EnemyInputComponent ic;
    private final BodyComponent playerBC;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();

    public AttackPlayerHoldingGroundActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerVel = eac.getPlayerEntity().getComponent(VelocityComponent.class);
        this.player = eac.getPlayerEntity().getComponent(PlayerComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute AttackPlayerHoldingGroundActionTask"));

        tmpVector2b.set(playerBC.x, playerBC.y);
        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.yellow("tmpCircle.contains(playerBC.x, playerBC.y) ")+Ansi.cyan(""+tmpCircle.contains(playerBC.x, playerBC.y)));

        if (bc.isContactedWithPlayer() && !player.isDead()) {

            ContactComponent playerContact = bc.getPlayerContact();
            player.setHealth(player.getHealth()-1);

            if (playerContact != null && playerContact.getNormal() != null) {
                if (playerContact.getNormal().x >= 1) {
                    playerVel.x += LATERAL_ACCELERATION;
//                playerVel.y += LATERAL_ACCELERATION;
                    if (player.getHealth() <= 0) {
                        player.setDeathFrom(-1);
                        ic.setLeft(false);
                        ic.setRight(false);
                        ic.setJump(false);
                        ic.setAnimation(AbstractPlayerInputComponent.Animation.IDLE);
                    }
                } else if (playerContact.getNormal().x <= -1) {
                    playerVel.x -= LATERAL_ACCELERATION;
//                playerVel.y += LATERAL_ACCELERATION;
                    if (player.getHealth() <= 0) {
                        player.setDeathFrom(1);
                        ic.setLeft(false);
                        ic.setRight(false);
                        ic.setJump(false);
                        ic.setAnimation(AbstractPlayerInputComponent.Animation.IDLE);
                    }
                }
            }

            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}