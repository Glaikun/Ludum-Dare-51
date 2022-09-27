package com.glaikunt.framework.isometric;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glaikunt.framework.esc.component.isometric.TileMapComponent;
import com.glaikunt.framework.DynamicDisplay;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.cache.TiledCache;

public class IsometricMapActor extends CommonActor {

    private final TileMapComponent tileMap;
    private final Polygon bounds;

    public IsometricMapActor(ApplicationResources applicationResources, Stage stage) {
        super(applicationResources);

        TiledMap tiledMap = applicationResources.getTiledMap(TiledCache.SOMETHING);

        MapProperties prop = tiledMap.getProperties();
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        int mapRows = prop.get("width", Integer.class);
        int mapColumns = prop.get("height", Integer.class);
        int mapHalfRows = prop.get("width", Integer.class) / 2;
        int mapHalfColumns = prop.get("height", Integer.class) / 2;
        int mapWidth = prop.get("width", Integer.class) * tilePixelWidth;
        int mapHeight = prop.get("height", Integer.class) * tilePixelHeight;

        float centerXPos = ((mapHalfRows - mapHalfColumns) * (tilePixelWidth / 2));
        float centerYPos = ((mapHalfRows + mapHalfColumns) * (tilePixelHeight / 2));
        this.pos.x = (DynamicDisplay.WORLD_WIDTH / 2) + centerXPos;
        this.pos.y = (DynamicDisplay.WORLD_HEIGHT / 2) + centerYPos;

        this.bounds = new Polygon();
        this.bounds.setVertices(new float[]{
                pos.x, pos.y,
                pos.x + (mapWidth / 2), pos.y - (mapHeight / 2),
                pos.x, pos.y - (mapHeight),
                pos.x - (mapWidth / 2), pos.y - (mapHeight / 2),
                pos.x, pos.y,
        });

        this.tileMap = new TileMapComponent();

        //http://clintbellanger.net/articles/isometric_math/
        for (int y = 0; y < mapColumns; y++) {
            for (int x = 0; x < mapRows; x++) {

                float xPos = pos.x + ((x - y) * (tilePixelWidth / 2));
                float yPos = pos.y - ((x + y) * (tilePixelHeight / 2));
                float xOrigin = pos.x + (((x - y) * (tilePixelWidth / 2)) - (tilePixelWidth/2));
                float yOrigin = pos.y - (((x + y) * (tilePixelHeight / 2)) + (tilePixelHeight));

                float[] vertices = new float[]{
                        xPos, yPos,
                        xPos + (tilePixelWidth / 2), yPos - (tilePixelHeight / 2),
                        xPos, yPos - (tilePixelHeight),
                        xPos - (tilePixelWidth / 2), yPos - (tilePixelHeight / 2),
                        xPos, yPos,
                };
                TilePolygon tilePolygon = new TilePolygon();
                tilePolygon.setVertices(vertices);
                tilePolygon.setOrigin(xOrigin, yOrigin);

                tileMap.addTilePolygonByOriginPosition(tilePolygon);
            }
        }

        getApplicationResources().getGlobalEntity().add(tileMap);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        for (Polygon polygon : tileMap.getTilePolygons().values()) {

            if (!polygon.contains(getApplicationResources().getFrontStageMousePosition().x, getApplicationResources().getFrontStageMousePosition().y)) {

                shapes.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, .1f);
                shapes.polygon(polygon.getVertices());
            } else  {

                shapes.setColor(Color.RED.r, Color.RED.g, Color.RED.b, .1f);
                shapes.polygon(polygon.getVertices());
            }

        }

        shapes.setColor(Color.YELLOW);
        shapes.polygon(bounds.getVertices());

        shapes.setColor(1, 1, 1, 1);
    }
}
