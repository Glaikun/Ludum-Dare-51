package com.glaikunt.framework.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

public class FontCache implements Cache {
    public static final String BASIC_FONT = "font/kenny_thick.fnt";

    private final Map<String, BitmapFont> fonts = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(BASIC_FONT, assetManager);
    }

    private void add(String filePath, AssetManager assetManager) {
        assetManager.load(filePath, BitmapFont.class);
        getFonts().put(filePath, null);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (fonts.isEmpty()) return false;

        for (String key : fonts.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : fonts.keySet()) {
                getFonts().put(key, (BitmapFont) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public BitmapFont getFontCache(String key) {
        return getFonts().get(key);
    }

    public Map<String, BitmapFont> getFonts() {
        return fonts;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
