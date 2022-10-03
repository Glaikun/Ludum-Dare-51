package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class TiledCache implements Cache {

    //   ################## EXAMPLE ##################
    public static final String DEBUG_MAP = "maps/debug_map.tmx";
    public static final String TRANSITION_DEBUG_MAP = "maps/debug_map_2.tmx";
//   ################## EXAMPLE ##################

    //   ################## LEVELS ##################
    public static final String LEVEL_1 = "maps/levels/level1.tmx";
    public static final String LEVEL_2 = "maps/levels/level2.tmx";
    public static final String LEVEL_3 = "maps/levels/level3.tmx";
    public static final String LEVEL_4 = "maps/levels/level4.tmx";
    //   ################## LEVELS ##################

    private final Map<String, TiledMap> tiledMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        TmxMapLoader loader = new TmxMapLoader(new InternalFileHandleResolver());
        assetManager.setLoader(TiledMap.class, loader);

        assetManager.load(DEBUG_MAP, TiledMap.class, params);
        getTiledMap().put(DEBUG_MAP, null);

        assetManager.load(TRANSITION_DEBUG_MAP, TiledMap.class, params);
        getTiledMap().put(TRANSITION_DEBUG_MAP, null);

        assetManager.load(LEVEL_1, TiledMap.class, params);
        getTiledMap().put(LEVEL_1, null);

        assetManager.load(LEVEL_2, TiledMap.class, params);
        getTiledMap().put(LEVEL_2, null);

        assetManager.load(LEVEL_3, TiledMap.class, params);
        getTiledMap().put(LEVEL_3, null);

        assetManager.load(LEVEL_4, TiledMap.class, params);
        getTiledMap().put(LEVEL_4, null);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (tiledMap.isEmpty()) return false;

        for (String key : tiledMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : tiledMap.keySet()) {
                getTiledMap().put(key, (TiledMap) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public TiledMap getTiledMapCache(String key) {
        return getTiledMap().get(key);
    }

    public Map<String, TiledMap> getTiledMap() {
        return tiledMap;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
