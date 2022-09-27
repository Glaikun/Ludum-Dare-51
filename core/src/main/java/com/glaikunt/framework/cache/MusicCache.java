package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.Map;

public class MusicCache implements Cache {

//   ################## EXAMPLE ##################
//   public static final String SOMETHING = "sounds/example.wav";
//   ################## EXAMPLE ##################

    private Map<String, Music> music = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

//        add(assetManager, SOMETHING);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (music.isEmpty()) return false;

        for (String key : music.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : music.keySet()) {
                getMusic().put(key, (Music) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public Music getSoundCache(String key) {
        return getMusic().get(key);
    }


    public void add(AssetManager assetManager, String... sounds) {
        for (String sound : sounds) {
            assetManager.load(sound, Music.class);
            getMusic().put(sound, null);
        }
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Map<String, Music> getMusic() {
        return music;
    }
}
