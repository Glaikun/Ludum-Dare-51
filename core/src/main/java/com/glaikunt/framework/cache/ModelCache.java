package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelCache implements Cache {

//   ################## EXAMPLE ##################
//   public static final String SOMETHING = "tmp/island.obj";
//   ################## EXAMPLE ##################

    private Map<String, Model> modelMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

//      add(assetManager, SOMETHING);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (modelMap.isEmpty()) return false;

        for (String key : modelMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : modelMap.keySet()) {
                getModelMap().put(key, (Model) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public void add(AssetManager assetManager, String... images) {
        for (String image : images) {
            assetManager.load(image, Model.class);
            getModelMap().put(image, null);
        }
    }

    public Model getModelCache(String key) {
        return getModelMap().get(key);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    private boolean isLoaded() {
        return loaded;
    }

    private void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
