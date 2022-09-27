package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.glaikunt.framework.application.Pair;

public class CacheRetriever {

    private boolean loaded = false;

    private AssetManager assetManager;
    private TextureCache textureCache;
    private SoundCache soundCache;
    private MusicCache musicCache;
    private TiledCache tiledCache;
    private FontCache fontCache;
    private ModelCache modelCache;

    public CacheRetriever() {

        this.assetManager = new AssetManager();
        this.textureCache = new TextureCache();
        this.soundCache = new SoundCache();
        this.musicCache = new MusicCache();
        this.tiledCache = new TiledCache();
        this.fontCache = new FontCache();
        this.modelCache = new ModelCache();
    }

    public void loadCache() {
        if (loaded) throw new IllegalArgumentException("Cache has already been retrieved");

        getTextureCache().loadCache(getAssetManager());
        getSoundCache().loadCache(getAssetManager());
        getMusicCache().loadCache(getAssetManager());
        getTiledCache().loadCache(getAssetManager());
        getFontCache().loadCache(getAssetManager());
        getModelCache().loadCache(getAssetManager());
        loaded = true;
    }

    public boolean isCacheLoaded() {

        return (getTextureCache().isLoaded(getAssetManager()) || getTextureCache().getTextureMap().isEmpty()) &&
                (getTiledCache().isLoaded(getAssetManager()) || getTiledCache().getTiledMap().isEmpty()) &&
                (getSoundCache().isLoaded(getAssetManager()) || getSoundCache().getSounds().isEmpty()) &&
                (getMusicCache().isLoaded(getAssetManager()) || getMusicCache().getMusic().isEmpty()) &&
                (getFontCache().isLoaded(getAssetManager()) || getFontCache().getFonts().isEmpty()) &&
                (getModelCache().isLoaded(getAssetManager()) || getModelCache().getModelMap().isEmpty());
    }

    public boolean update() {
        return getAssetManager().update();
    }

    public float progress() {
        return getAssetManager().getProgress();
    }

    public Texture geTextureCache(String key) {
        return getTextureCache().getTextureCache(key);
    }

    public TextureRegion geTextureRegionCache(Pair<Integer, Integer> key) {
        return getTextureCache().getTextureRegionCache(key);
    }

    public TiledMap getTiledMapCache(String key) {
        return getTiledCache().getTiledMapCache(key);
    }

    public BitmapFont getFontCache(String key) {
        return getFontCache().getFontCache(key);
    }

    public Sound getSoundCache(String key) {
        return getSoundCache().getSoundCache(key);
    }

    private TextureCache getTextureCache() {
        return textureCache;
    }

    public SoundCache getSoundCache() {
        return soundCache;
    }

    public MusicCache getMusicCache() {
        return musicCache;
    }


    public TiledCache getTiledCache() {
        return tiledCache;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public FontCache getFontCache() {
        return fontCache;
    }

    public ModelCache getModelCache() {
        return modelCache;
    }

    public void dispose() {
        assetManager.dispose();
    }
}
