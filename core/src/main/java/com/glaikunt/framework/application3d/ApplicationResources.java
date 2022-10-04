package com.glaikunt.framework.application3d;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.glaikunt.framework.application.AudioManager;
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
    private final Game display;

    public ApplicationResources(Game display) {
        this.display = display;
        this.engine = new Engine();
        this.audioManager = new AudioManager();
        this.preferences = Gdx.app.getPreferences("glaikuntDatabase");
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

    public Game getDisplay() {
        return display;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void dispose() {
        cacheRetriever.dispose();
    }

    public Texture getTexture(String cache) {
        return getCacheRetriever().geTextureCache(cache);
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

}
