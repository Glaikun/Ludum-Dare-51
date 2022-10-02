package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.game.map.HeatSourceActor;

public class MoveToNearestHeatSourceActionTask extends AbstractLeafTask {

    private final EasyAccessComponent eac;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final EnemyInputComponent ic;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Vector2 tmpVector2Target = new Vector2();
    public MoveToNearestHeatSourceActionTask(Entity entity) {
        super(entity);
        this.eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute MoveToNearestHeatSourceActionTask"));
        System.out.println( Ansi.red("  |- ")+Ansi.purple("Previous target? ")+Ansi.cyan(""+tc.getTargetHeatSource()));
        // TODO if we're sure the heat source target is still valid, use that and don't recompute the below
        tmpVector2a.set(bc.x, bc.y);
        System.out.println( Ansi.red("  |- ")+Ansi.purple("bodyC: ")+Ansi.yellow(bc+" => "+tmpVector2a));
        float dist = Float.MAX_VALUE;
        tmpVector2Target.set(0,0);
        for (HeatSourceActor hs : eac.getCurrentLevel().getHeatSources()) {
            tmpVector2b.set(hs.getX(), hs.getY());
            float d = tmpVector2b.sub(tmpVector2a).len();
            if (d < dist) {
                dist = d;
                tmpVector2Target.set(hs.getX(), hs.getY());
                System.out.println( Ansi.red("  |  |- ")+Ansi.purple("heatsource ["+hs.getX()+","+hs.getY()+"] range: ")+Ansi.yellow(""+d)+Ansi.cyan(" "+tmpVector2b));
            }
        }
        if (!tmpVector2Target.equals(Vector2.Zero)) {
            tc.setTargetHeatSource(tmpVector2Target);
            tmpVector2b.set(tmpVector2Target).sub(tmpVector2a);
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
            System.out.println( Ansi.red("  |  |- ")+Ansi.green("heatsource ["+tmpVector2Target+"] "+tmpVector2b));
            System.out.println( Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        }

        return Status.FAILED;
    }
}