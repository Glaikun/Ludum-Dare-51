package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class AttackPlayerActionTask extends AbstractLeafTask {

    private static final int RADIUS = 32*32; // tiles?
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final EnemyInputComponent ic;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();

    public AttackPlayerActionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute AttackPlayerActionTask"));
        tmpVector2a.set(bc.x, bc.y);
        tmpVector2b.set(playerBC.x, playerBC.y);
        tc.setTargetPlayer(tmpVector2b); // this

        tmpCircle.set(bc.x, bc.y, RADIUS);

        System.out.println( Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" worth attacking?: "+tmpCircle.contains(playerBC.x, playerBC.y)+" testing against radius ")+Ansi.yellow(RADIUS));
        if (tmpCircle.contains(playerBC.x, playerBC.y)) {
            tmpVector2b.sub(tmpVector2a);
            int x = Math.round(tmpVector2b.x);
            int y = Math.round(tmpVector2b.y);
            if (x < 0) {
                ic.setLeft(true);
                ic.setRight(false);
                ic.setJump(false);
            } else if (x > 0) {
                ic.setLeft(false);
                ic.setRight(true);
                ic.setJump(false);
            } else if (y < 0) {
                ic.setLeft(false);
                ic.setRight(false);
                ic.setJump(true);
            } else {
                ic.setLeft(false);
                ic.setRight(false);
                ic.setJump(false);
            }
            System.out.println( Ansi.red("  |- ")+Ansi.purple("dir: ")+Ansi.yellow(tmpVector2b.toString())+Ansi.green(" Status.SUCCEEDED"));
            return Status.FAILED;
        } else {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}

