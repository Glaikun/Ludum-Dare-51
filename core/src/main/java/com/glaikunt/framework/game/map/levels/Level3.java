package com.glaikunt.framework.game.map.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.cache.TiledCache;

public class Level3 extends AbstractLevel {

    public Level3(ApplicationResources applicationResources, Stage front) {
        super(applicationResources, front, TiledCache.LEVEL_3);
    }
}
