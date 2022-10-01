package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TiledCache;

public class DebugLevel extends CommonActor {

    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer background;

    public DebugLevel(ApplicationResources applicationResources) {
        super(applicationResources);

        TiledMap map = applicationResources.getTiledMap(TiledCache.SOMETHING);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.background = (TiledMapTileLayer) map.getLayers().get("Background");

        MapLayer level_collision = map.getLayers().get("Platforms");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        renderer.getBatch().begin();
        renderer.renderTileLayer(background);
        renderer.getBatch().end();
    }

    @Override
    public void act(float delta) {

        if (getStage() != null) {
            renderer.setView((OrthographicCamera) getStage().getCamera());
        }
    }
}
