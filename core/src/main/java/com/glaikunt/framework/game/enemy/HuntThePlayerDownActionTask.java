package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class HuntThePlayerDownActionTask extends AbstractLeafTask {
    private static final int RADIUS = 32*32; // tiles?
    private final BodyComponent playerBC;
    private final BodyComponent bc;

    private final TargetsComponent tc;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();
    public HuntThePlayerDownActionTask(Entity entity) {
        super(entity);
        EasyAccessComponent eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute HuntThePlayerDownActionTask"));
        tmpVector2a.set(bc.x, bc.y);
        tmpVector2b.set(playerBC.x, playerBC.y);
        tc.setTargetPlayer(tmpVector2b); // this

        tmpCircle.set(bc.x, bc.y, RADIUS);

        System.out.println( Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" worth hunting?: "+tmpCircle.contains(playerBC.x, playerBC.y)+" testing against radius ")+Ansi.yellow(RADIUS));
        if (tmpCircle.contains(playerBC.x, playerBC.y)) {
            System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}