package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;

public interface Cache {

    void loadCache(AssetManager assetManager);
    boolean isLoaded(AssetManager assetManager);
}
