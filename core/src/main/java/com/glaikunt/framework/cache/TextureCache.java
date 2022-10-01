package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.glaikunt.framework.application.Pair;

import java.util.HashMap;
import java.util.Map;

public class TextureCache implements Cache {

//   ################## EXAMPLE ##################
    public static final String PIXEL = "tmp/pixel.png";
//   ################## EXAMPLE ##################

    //   ################## SPRITE TILES ##################
    public static final String SPRITESHEET = "spritesheet/spritesheet.png";
    public static final Pair<Integer, Integer> BLOCK = Pair.of(0, 0);
    //   ################## SPRITE TILES ##################

//   ################## PLAYER ##################
    public static final String PLAYER = "player/player.png";
//   ################## PLAYER ##################

    public static final String ENEMY = "enemy/enemy.png";
    public static final String HEATSOURCE = "heatsource/heatsource.png";

    private Map<String, Texture> textureMap = new HashMap<>();
    private Map<String, TextureRegion[][]> textureRegionMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(assetManager, PIXEL, SPRITESHEET, PLAYER, ENEMY, HEATSOURCE);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (textureMap.isEmpty()) return false;

        for (String key : textureMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : textureMap.keySet()) {
                getTextureMap().put(key, (Texture) assetManager.get(key));
                if (key.contains("spritesheet/")) {
                    getTextureRegionMap().put(key, TextureRegion.split(getTextureCache(key), 32, 32));
                }
            }
            setLoaded(true);
        }

        return true;
    }

    public void add(AssetManager assetManager, String... images) {
        for (String image : images) {
            assetManager.load(image, Texture.class);
            getTextureMap().put(image, null);
        }
    }

    public Texture getTextureCache(String key) {
        return getTextureMap().get(key);
    }

    public Map<String, Texture> getTextureMap() {
        return textureMap;
    }

    public TextureRegion getTextureRegionCache(Pair<Integer, Integer> key) {
        return getTextureRegionMap().get(SPRITESHEET)[key.getKey()][key.getValue()];
    }

    public Map<String, TextureRegion[][]> getTextureRegionMap() {
        return textureRegionMap;
    }

    private boolean isLoaded() {
        return loaded;
    }

    private void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
