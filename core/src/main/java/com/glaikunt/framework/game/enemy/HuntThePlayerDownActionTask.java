package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.game.GameConstants;

public class HuntThePlayerDownActionTask extends AbstractLeafTask {
    private static final int RADIUS = 32*32; // tiles?
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final EnemyInputComponent ic;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();
    public HuntThePlayerDownActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute HuntThePlayerDownActionTask"));
        tmpVector2a.set(bc.x, bc.y);
        tmpVector2b.set(playerBC.x, playerBC.y);
        tc.setTargetPlayer(tmpVector2b); // this

        tmpCircle.set(bc.x, bc.y, RADIUS);

        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" worth hunting?: "+tmpCircle.contains(playerBC.x, playerBC.y)+" testing against radius ")+Ansi.yellow(RADIUS));
        if (tmpCircle.contains(playerBC.x, playerBC.y)) {
            tmpVector2b.sub(tmpVector2a);
            int x = Math.round(tmpVector2b.x);
            int y = Math.round(tmpVector2b.y);
            if (x < 0) {
                ic.setLeft(true);
                ic.setRight(false);
            } else if (x > 0) {
                ic.setLeft(false);
                ic.setRight(true);
            } else {
                ic.setLeft(false);
                ic.setRight(false);
            }
            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.purple("dir: ")+Ansi.yellow(tmpVector2b.toString())+Ansi.green(" Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}