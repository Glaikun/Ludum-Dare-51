package com.glaikunt.framework.esc.component.isometric;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.isometric.TilePolygon;
import com.glaikunt.framework.pathfinding.Mover;
import com.glaikunt.framework.pathfinding.TileBasedMap;

import java.util.HashMap;
import java.util.Map;

public class TileMapComponent implements Component, TileBasedMap {

    private final Map<Vector2, TilePolygon> tilePolygons = new HashMap<>();
    private TilePolygon[][] binaryMap;

    public void addTilePolygonByOriginPosition(TilePolygon tilePolygon) {
        this.tilePolygons.put(new Vector2(tilePolygon.getOriginX(), tilePolygon.getOriginY()), tilePolygon);
    }

    public Map<Vector2, TilePolygon> getTilePolygons() {
        return tilePolygons;
    }

    @Override
    public boolean blocked(Mover mover, int x, int y) {
        return binaryMap[y][x] == null || binaryMap[y][x].isBlocked();
    }

    @Override
    public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
        return Math.abs(sx - tx) + Math.abs(sy - ty);
    }

    @Override
    public int getHeightInTiles() {
        return binaryMap.length;
    }

    @Override
    public int getWidthInTiles() {
        return binaryMap[0].length;
    }

    @Override
    public void pathFinderVisited(int x, int y) {

    }
}
