package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.component.common.DamageComponent;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.movement.EnemyInputComponent;
import com.glaikunt.framework.esc.system.physics.BodyComponent;


public class AttackBreakableActionTask extends AbstractLeafTask {
    private final EasyAccessComponent eac;
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private final TargetsComponent tc;
    private final DamageComponent dc;
    private final EnemyInputComponent ic;
    public AttackBreakableActionTask(Entity entity) {
        super(entity);
        this.eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
        this.tc = entity.getComponent(TargetsComponent.class);
        this.ic = entity.getComponent(EnemyInputComponent.class);
        this.dc = entity.getComponent(DamageComponent.class);
    }

    @Override
    public Status execute() {
        System.out.println( Ansi.red("[AI] ")+Ansi.yellow("execute AttackBreakableActionTask"));
        if (tc.getTargetBreakable() == null) {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED (no target)"));
            return Status.FAILED;
        }
        System.out.println( Ansi.red("  |- ")+Ansi.purple("target is: ")+Ansi.cyan(""+tc.getTargetBreakable().getComponent(HealthComponent.class).getHealthPercent()+"%"));
        if (tc.getTargetBreakable().getComponent(HealthComponent.class).isExpired()) {
            System.out.println( Ansi.red("  |- ")+Ansi.red("Status.FAILED (can't attack a broken thing)"));
            eac.getCurrentLevel().removeBreakable(tc.getTargetBreakable());
            tc.setTargetBreakable(null);
            return Status.FAILED;
        }

        System.out.println( Ansi.red("  |- ")+Ansi.green("Knock knock, Neo ")+Ansi.cyan("DMG: "+dc.getDamage())+" // TODO remember cooldown time!");
        tc.getTargetBreakable().getComponent(HealthComponent.class).damage(dc.getDamage());

        if (tc.getTargetBreakable().getComponent(HealthComponent.class).isExpired()) {
            eac.getCurrentLevel().removeBreakable(tc.getTargetBreakable());
            tc.setTargetBreakable(null);
            ic.setJump(true);
            System.out.println( Ansi.red("  |- ")+Ansi.green("Reminder of TC now: ")+Ansi.cyan(tc.toString()));
        }

        return Status.SUCCEEDED;
    }
}