package com.glaikunt.framework.cache;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class SoundCache implements Cache {

   public static final String SMASH = "sound/148074__sandyrb__lightbulb-smash-002.ogg";

    public static final ImmutableArray<String> BANGING_DOOR_VARIANTS = new ImmutableArray<>(new Array<>(new String[]{
            "sound/85541__maj061785__banging-screen-door-1.ogg",
            "sound/85541__maj061785__banging-screen-door-2.ogg"
    }));

    private final Map<String, Sound> sounds = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(assetManager, SMASH);
        add(assetManager, BANGING_DOOR_VARIANTS.toArray());
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
            for (String key : sounds.keySet()) {
                getSounds().put(key, (Sound) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public Sound getSoundCache(String key) {
        return getSounds().get(key);
    }


    public void add(AssetManager assetManager, String... sounds) {
        for (String sound : sounds) {
            assetManager.load(sound, Sound.class);
            getSounds().put(sound, null);
        }
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Map<String, Sound> getSounds() {
        return sounds;
    }
}
