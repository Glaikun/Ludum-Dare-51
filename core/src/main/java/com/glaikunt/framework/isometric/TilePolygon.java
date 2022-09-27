package com.glaikunt.framework.isometric;

import com.badlogic.gdx.math.Polygon;

public class TilePolygon extends Polygon {

    private boolean blocked = false;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
