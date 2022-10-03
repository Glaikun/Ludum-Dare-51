package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.game.GameConstants;

public class BehaviourFactory {

    private BehaviourFactory() {}
    public static Task<Entity> getBehaviour(Stance stance, Entity entity, ApplicationResources applicationResources) {
        if (GameConstants.BEHAVIOUR_LOGGING) System.out.println( Ansi.red("[AI]] ")+Ansi.purple("Stance: ")+Ansi.yellow(stance.name()));
        switch (stance) {
            case DEFENSIVE: return defensiveBehaviour(entity, applicationResources);
            case PASSIVE: return passiveBehaviour(entity, applicationResources);
            case AGGRESSIVE: return aggressiveBehaviour(entity, applicationResources);
            case AGGRESSIVE_WARM: return aggressiveWarmerBehaviour(entity, applicationResources);
            default: return null;
        }
    }
    /**
     * Priorities:
     *  - find heatsource if too cold
     *  - holding ground, only attack if player too close
     * @return
     */
    private static Task<Entity> defensiveBehaviour(Entity entity, ApplicationResources applicationResources) {
        Sequence<Entity> playerStateSequence = new Sequence<>(
                new PlayerDeadConditionTask(entity, applicationResources),
                new EatPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> breakObstaclesSequence = new Sequence<>(
                new IsBreakableDirectlyBlockingPathConditionTask(entity, applicationResources),
                new AttackBreakableActionTask(entity, applicationResources)
        );
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity, applicationResources),
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        Sequence<Entity> defensiveAttackSequence = new Sequence<>(
                new IsWarmConditionTask(entity, applicationResources),
                new AttackPlayerHoldingGroundActionTask(entity, applicationResources)
        );

        return new Selector<>(
                new LevelCompleteConditionTask(entity, applicationResources),
                playerStateSequence,
                breakObstaclesSequence,
                findHeatSourceSequence,
                defensiveAttackSequence
        );
    }

    /**
     * Priorities:
     *  - find heatsource if too cold
     *  - wander without intention if warm
     *  - attack if player close
     * @return
     */
    private static Task<Entity> passiveBehaviour(Entity entity, ApplicationResources applicationResources) {
        Sequence<Entity> playerStateSequence = new Sequence<>(
                new PlayerDeadConditionTask(entity, applicationResources),
                new EatPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> breakObstaclesSequence = new Sequence<>(
                new IsBreakableDirectlyBlockingPathConditionTask(entity, applicationResources),
                new AttackBreakableActionTask(entity, applicationResources)
        );
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity, applicationResources),
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        Sequence<Entity> wanderIfWarmSequence = new Sequence<>(
                new IsWarmConditionTask(entity, applicationResources),
                new WanderAimlesslyActionTask(entity, applicationResources)
        );
        Sequence<Entity> attackIfCloseSequence = new Sequence<>(
                new IsWarmConditionTask(entity, applicationResources),
                new PlayerNearbyConditionTask(entity, applicationResources),
                new AttackPlayerActionTask(entity, applicationResources)
        );

        return new Selector<>(
                new LevelCompleteConditionTask(entity, applicationResources),
                playerStateSequence,
                breakObstaclesSequence,
                findHeatSourceSequence,
                wanderIfWarmSequence,
                attackIfCloseSequence
        );
    }

    /**
     * Priorities:
     *  - attack if player close
     *  - seek player if warm
     *  - find heatsource if too cold
     * @return
     */
    private static Task<Entity> aggressiveBehaviour(Entity entity, ApplicationResources applicationResources) {
        Sequence<Entity> playerStateSequence = new Sequence<>(
                new PlayerDeadConditionTask(entity, applicationResources),
                new EatPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> attackIfCloseSequence = new Sequence<>(
                new NotTooColdConditionTask(entity, applicationResources),
                new PlayerNearbyConditionTask(entity, applicationResources),
                new AttackPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> breakObstaclesSequence = new Sequence<>(
                new IsBreakableDirectlyBlockingPathConditionTask(entity, applicationResources),
                new AttackBreakableActionTask(entity, applicationResources)
        );
        Sequence<Entity> seekPlayerIfWarmSequence = new Sequence<>(
                new IsWarmConditionTask(entity, applicationResources),
                new HuntThePlayerDownActionTask(entity, applicationResources)
        );
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new TooColdConditionTask(entity, applicationResources),
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        Sequence<Entity> fallback = new Sequence<>(
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        return new Selector<>(
                new LevelCompleteConditionTask(entity, applicationResources),
                playerStateSequence,
                attackIfCloseSequence,
                breakObstaclesSequence,
                seekPlayerIfWarmSequence,
                findHeatSourceSequence,
                fallback
        );
    }

    /**
     * Priorities:
     *  - attack if player close
     *  - seek player if warm
     *  - find heatsource if too cold
     * @return
     */
    private static Task<Entity> aggressiveWarmerBehaviour(Entity entity, ApplicationResources applicationResources) {
        Sequence<Entity> playerStateSequence = new Sequence<>(
                new PlayerDeadConditionTask(entity, applicationResources),
                new EatPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> findHeatSourceSequence = new Sequence<>(
                new HeatUpConditionTask(entity, applicationResources),
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        Sequence<Entity> attackIfCloseSequence = new Sequence<>(
                new NotTooColdConditionTask(entity, applicationResources),
                new PlayerNearbyConditionTask(entity, applicationResources),
                new AttackPlayerActionTask(entity, applicationResources)
        );
        Sequence<Entity> breakObstaclesSequence = new Sequence<>(
                new IsBreakableDirectlyBlockingPathConditionTask(entity, applicationResources),
                new AttackBreakableActionTask(entity, applicationResources)
        );
        Sequence<Entity> seekPlayerIfWarmSequence = new Sequence<>(
                new IsWarmConditionTask(entity, applicationResources),
                new HuntThePlayerDownActionTask(entity, applicationResources)
        );
        Sequence<Entity> fallback = new Sequence<>(
                new MoveToNearestHeatSourceActionTask(entity, applicationResources)
        );
        return new Selector<>(
                new LevelCompleteConditionTask(entity, applicationResources),
                playerStateSequence,
                attackIfCloseSequence,
                breakObstaclesSequence,
                seekPlayerIfWarmSequence,
                findHeatSourceSequence,
                fallback
        );
    }
}
