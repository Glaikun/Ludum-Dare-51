package com.glaikunt.framework.game.map.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TiledCache;

public class NextDebugLevel extends AbstractLevel {

    public NextDebugLevel(ApplicationResources applicationResources, Stage front) {
        super(applicationResources, front, TiledCache.TRANSITION_DEBUG_MAP);
    }
}
