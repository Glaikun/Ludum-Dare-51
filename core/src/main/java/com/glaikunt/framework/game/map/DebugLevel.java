package com.glaikunt.framework.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TiledCache;
import com.glaikunt.framework.game.player.PlayerActor;

public class DebugLevel extends CommonActor {

    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer background;

    public DebugLevel(ApplicationResources applicationResources, Stage front) {
        super(applicationResources);

        TiledMap map = applicationResources.getTiledMap(TiledCache.SOMETHING);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.background = (TiledMapTileLayer) map.getLayers().get("Background");

        TiledMapTileLayer player_start = (TiledMapTileLayer) map.getLayers().get("Player");
        MapLayer level_collision = map.getLayers().get("Platforms");

        for (int y = player_start.getHeight(); y >= 0; y--) {
            float yPos = (y * player_start.getTileHeight());
            for (int x = 0; x < player_start.getWidth(); x++) {
                float xPos = (x * player_start.getTileWidth());

                TiledMapTileLayer.Cell playerStartCell = player_start.getCell(x, y);
                if (playerStartCell != null) {

                    front.addActor(new PlayerActor(applicationResources,  new Vector2(xPos, yPos)));
                }
            }
        }

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