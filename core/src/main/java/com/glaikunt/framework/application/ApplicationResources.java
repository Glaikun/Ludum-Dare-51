package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.cache.CacheRetriever;

public class ApplicationResources {

    private final Entity immutableGameEntity = new Entity();
    private final Entity globalEntity = new Entity();
    private final CacheRetriever cacheRetriever = new CacheRetriever();
    private final Vector3 frontStageMousePosition = new Vector3();
    private final Vector3 uxStageMousePosition = new Vector3();
    private final Preferences preferences;
    private final AudioManager audioManager;
    private final Engine engine;
    private final Display display;
    private final World world;
    private boolean enableBox2d;

    public ApplicationResources(Display display) {
        this.display = display;
        this.engine = new Engine();
        this.audioManager = new AudioManager();
        this.world = new World(new Vector2(0, -9.8f), true);
        this.preferences = Gdx.app.getPreferences("glaikuntDatabase");
        this.enableBox2d = false;
    }

    public Engine getEngine() {
        return engine;
    }

    public Entity getGlobalEntity() {
        return globalEntity;
    }

    public Entity getImmutableGameEntity() {
        return immutableGameEntity;
    }

    public CacheRetriever getCacheRetriever() {
        return cacheRetriever;
    }

    public Vector3 getFrontStageMousePosition() {
        return frontStageMousePosition;
    }

    public Vector3 getUxStageMousePosition() {
        return uxStageMousePosition;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public Display getDisplay() {
        return display;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void dispose() {
        cacheRetriever.dispose();
    }

    public World getWorld() {
        return world;
    }

    public Texture getTexture(String cache) {
        return getCacheRetriever().geTextureCache(cache);
    }

    public TextureRegion geTextureRegionCache(Pair<Integer, Integer> key) {
        return getCacheRetriever().geTextureRegionCache(key);
    }

    public BitmapFont getFont(String cache) {
        return getCacheRetriever().getFontCache(cache);
    }

    public TiledMap getTiledMap(String cache) {
        return getCacheRetriever().getTiledMapCache(cache);
    }

    public Sound getSound(String cache) {
        return getCacheRetriever().getSoundCache().getSoundCache(cache);
    }

    public void setEnableBox2d(boolean enableBox2d) {
        this.enableBox2d = enableBox2d;
    }

    public boolean isEnableBox2d() {
        return enableBox2d;
    }
}
