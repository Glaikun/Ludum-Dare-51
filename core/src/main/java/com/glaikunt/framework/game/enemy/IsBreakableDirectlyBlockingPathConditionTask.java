package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;
import com.glaikunt.framework.game.map.BreakableActor;

public class IsBreakableDirectlyBlockingPathConditionTask extends AbstractLeafTask {

    private static final int RADIUS = 32; // tile
    private final EasyAccessComponent eac;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final Vector2 tmpVector2a = new Vector2();
    private final Vector2 tmpVector2b = new Vector2();
    private final Circle tmpCircle = new Circle();
    public IsBreakableDirectlyBlockingPathConditionTask(Entity entity, ApplicationResources applicationResources) {
        super(entity, applicationResources);
        this.eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute IsBreakableDirectlyBlockingPathConditionTask"));
        if (eac.getCurrentLevel().getBreakables().isEmpty()) {
            if (tc.getTargetBreakable() != null) {
                tc.setTargetBreakable(null);
            }
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED"));
            return Status.FAILED;
        }

        System.out.println( Ansi.red("  |- ")+Ansi.purple("Previous target? ")+Ansi.cyan(""+tc.getTargetBreakable()));
        tmpVector2a.set(bc.x, bc.y);
        tmpCircle.set(bc.x, bc.y, RADIUS);
        System.out.println( Ansi.red("  |- ")+Ansi.purple("bodyC: ")+Ansi.yellow(bc+" => "+tmpVector2a));
        float dist = Float.MAX_VALUE;

        for (BreakableActor b : eac.getCurrentLevel().getBreakables()) {
            if (tmpCircle.contains(b.getX(), b.getY())) {
                System.out.println( Ansi.red("  |- ")+Ansi.green("Found a breakable next to me ")+Ansi.cyan(""+b.getEntity().getComponent(HealthComponent.class).getHealthPercent()+"%"));
                if (b.getEntity().getComponent(HealthComponent.class).isExpired()) {
                    if (tc.getTargetBreakable() == b.getEntity()) {
                        tc.setTargetBreakable(null);
                    }
                    continue; // can't smash a dead thing
                }
                tmpVector2b.set(b.getX(), b.getY());
                float d = tmpVector2b.sub(tmpVector2a).len();
                if (d < dist) {
                    dist = d;
                    tc.setTargetBreakable(b.getEntity());
                    System.out.println(Ansi.red("  |  |- ") + Ansi.purple("breakable [" + b.getX() + "," + b.getY() + "] range: ") + Ansi.yellow("" + d) + Ansi.cyan(" " + tmpVector2b));
                }
            }
        }
        if (tc.getTargetBreakable() == null) {
            System.out.println(Ansi.red("  |- ") + Ansi.red("Status.FAILED"));
            return Status.FAILED;
        } else {
            System.out.println(Ansi.red("  |- ") + Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        }
    }
}