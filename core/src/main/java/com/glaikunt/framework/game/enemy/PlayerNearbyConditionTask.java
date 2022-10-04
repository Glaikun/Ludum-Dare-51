package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.game.GameConstants;

public class PlayerNearbyConditionTask extends AbstractLeafTask {

    private static final int RADIUS = 32*6; // tiles?
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private final EnemyInputComponent ic;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();
    public PlayerNearbyConditionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute PlayerNearbyConditionTask"));

        tmpVector2a.set(bc.x, bc.y);
        tmpVector2b.set(playerBC.x, playerBC.y);
        tmpCircle.set(bc.x, bc.y, RADIUS);

        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" in sight?: "+tmpCircle.contains(playerBC.x, playerBC.y)+" testing against radius ")+Ansi.yellow(RADIUS));
        if (tmpCircle.contains(playerBC.x, playerBC.y)) {
            tmpVector2b.sub(tmpVector2a);
            int x = Math.round(tmpVector2b.x);
//            int y = Math.round(tmpVector2b.y);
            if (x < 0) {
                ic.setLeft(true);
                ic.setRight(false);
                ic.setJump(false);
            } else if (x > 0) {
                ic.setLeft(false);
                ic.setRight(true);
                ic.setJump(false);
            } else {
                ic.setLeft(false);
                ic.setRight(false);
                ic.setJump(false);
            }
            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}
