package com.glaikunt.framework.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.glaikunt.framework.game.player.PlayerActor;

public interface Level {

    PlayerActor getPlayer();
    Array<HeatSourceActor> getHeatSources();
    Array<BreakableActor> getBreakables();

    void removeBreakable(Entity entity);
}
