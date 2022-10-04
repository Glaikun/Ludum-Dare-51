package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.Map;

public class MusicCache implements Cache {

   public static final String BLIZZARD_EXTERNAL = "sound/573290__kevp888__210523-1598-fr-blizzard.ogg";
   public static final String BLIZZARD_INTERNAL = "sound/173096__stormpetrel__whistling-antarctic-blizzard.ogg"; // real Antarctic SFX!

    private final Map<String, Music> sounds = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(assetManager, BLIZZARD_EXTERNAL);
        add(assetManager, BLIZZARD_INTERNAL);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (sounds.isEmpty()) return false;

        for (String key : sounds.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            sounds.replaceAll((k, v) -> assetManager.get(k));
            setLoaded(true);
        }

        return true;
    }

    public Music getMusicPiece(String key) {
        return sounds.get(key);
    }


    public void add(AssetManager assetManager, String... sfx) {
        for (String sound : sfx) {
            assetManager.load(sound, Music.class);
            sounds.put(sound, null);
        }
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Map<String, Music> getMusic() {
        return sounds;
    }
}

