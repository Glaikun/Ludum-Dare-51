package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class HuntThePlayerDownActionTask extends AbstractLeafTask {
    private static final int RANGE = 32*6; // tiles?
    private final EasyAccessComponent eac;
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    public HuntThePlayerDownActionTask(Entity entity) {
        super(entity);
        this.eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute HuntThePlayerDownActionTask"));
        bc.getCenter(tmpVector2a);
        playerBC.getCenter(tmpVector2b);
        // TODO not just one sqrt warning but two if you keep this debug!
        System.out.println( Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" dist: "+tmpVector2b.sub(tmpVector2a).len()+" testing against range ")+Ansi.yellow(RANGE));
        if (tmpVector2b.sub(tmpVector2a).len() < RANGE) {
            System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            // TODO actually move closer
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }
    }
}