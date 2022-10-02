package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.glaikunt.framework.Ansi;

public class BehaviourFactory {

    private BehaviourFactory() {}
    public static Task<Entity> getBehaviour(Stance stance, Entity entity) {
        System.out.println( Ansi.red("[AI]] ")+Ansi.purple("Stance: ")+Ansi.yellow(stance.name()));
        switch (stance) {
            case DEFENSIVE: return defensiveBehaviour(entity);
            case PASSIVE: return passiveBehaviour(entity);
            case AGGRESSIVE: return aggressiveBehaviour(entity);
            default: return null;
        }
    }
    /**
     * Priorities:
     *  - find heatsource if too cold
     *  - holding ground, only attack if player too close
     * @return
     */
    private static Task<Entity> defensiveBehaviour(Entity entity) {

        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity),
                new MoveToNearestHeatSourceActionTask(entity)
        );
        Sequence<Entity> defensiveAttackSequence = new Sequence<>(
                new PlayerNearbyConditionTask(entity),
                new AttackPlayerHoldingGroundActionTask(entity)
        );

        return new Selector<>(findHeatSourceSequence, defensiveAttackSequence);
    }

    /**
     * Priorities:
     *  - find heatsource if too cold
     *  - wander without intention if warm
     *  - attack if player close
     * @return
     */
    private static Task<Entity> passiveBehaviour(Entity entity) {
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity),
                new MoveToNearestHeatSourceActionTask(entity)
        );
        Sequence<Entity> wanderIfWarmSequence = new Sequence<>(
                new IsWarmConditionTask(entity),
                new WanderAimlesslyActionTask(entity)
        );
        Sequence<Entity> attackIfCloseSequence = new Sequence<>(
                new PlayerNearbyConditionTask(entity),
                new AttackPlayerActionTask(entity)
        );

        return new Selector<>(findHeatSourceSequence, wanderIfWarmSequence, attackIfCloseSequence);
    }

    /**
     * Priorities:
     *  - attack if player close
     *  - seek player if warm
     *  - find heatsource if too cold
     * @return
     */
    private static Task<Entity> aggressiveBehaviour(Entity entity) {

        Sequence<Entity> attackIfCloseSequence = new Sequence<>(
                new PlayerNearbyConditionTask(entity),
                new AttackPlayerActionTask(entity)
        );
        Sequence<Entity> seekPlayerIfWarmSequence = new Sequence<>(
                new IsWarmConditionTask(entity),
                new HuntThePlayerDownActionTask(entity)
        );
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity),
                new MoveToNearestHeatSourceActionTask(entity)
        );
        return new Selector<>(attackIfCloseSequence, seekPlayerIfWarmSequence, findHeatSourceSequence);
    }
}
