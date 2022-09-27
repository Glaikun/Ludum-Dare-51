package com.glaikunt.framework.pathfinding.heuristics;


import com.glaikunt.framework.pathfinding.AStarHeuristic;
import com.glaikunt.framework.pathfinding.Mover;
import com.glaikunt.framework.pathfinding.TileBasedMap;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 * 
 * @author Kevin Glass, modifications Vault101.co.uk
 */
public class ClosestHeuristic implements AStarHeuristic {
	/**
	 * @see AStarHeuristic#getCost(TileBasedMap, Mover, int, int, int, int)
	 */
	public float getCost(final TileBasedMap map, final Mover mover, final int x, final int y, final int tx, final int ty) {
		final float dx = tx - x;
		final float dy = ty - y;
		
		final float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		return result;
	}

}
