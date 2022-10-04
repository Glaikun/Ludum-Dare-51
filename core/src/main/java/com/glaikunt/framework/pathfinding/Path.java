package com.glaikunt.framework.pathfinding;

import java.util.ArrayList;

/**
 * A path determined by some path finding algorithm. A series of steps from
 * the starting location to the target location. This includes a step for the
 * initial location.
 * 
 * @author Kevin Glass with modifications from Vault101 / Peter D Bell
 */
public class Path {
	/**
	 * A single step within the path
	 * 
	 * @author Kevin Glass with modifications from Vault101 / Peter D Bell
	 */
	public static class Step {
		/** The x coordinate at the given step */
		private final int x;
		/** The y coordinate at the given step */
		private final int y;
		/** cost - for debugging */
		private final float cost;
		
		/**
		 * Create a new step
		 * 
		 * @param x The x coordinate of the new step
		 * @param y The y coordinate of the new step
		 */
		public Step(final int x, final int y, final float cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		
		/**
		 * @see Object#equals(Object)
		 */
		public boolean equals(final Object other) {
			if (other instanceof Step) {
				final Step o = (Step) other;
				return (o.x == x) && (o.y == y);
			}
			
			return false;
		}

		/**
		 * Get the x coordinate of the new step
		 * 
		 * @return The x coodindate of the new step
		 */
		public int getX() {
			return x;
		}
		
		/**
		 * Get the y coordinate of the new step
		 * 
		 * @return The y coodindate of the new step
		 */
		public int getY() {
			return y;
		}

		/**
		 * @see Object#hashCode()
		 */
		public int hashCode() {
			return x*y;
		}

	}

	/** The list of steps building up this path */
	private final ArrayList<Step> steps = new ArrayList<>();

	/**
	 * Create an empty path
	 */
	public Path() {
	}
	
	/**
	 * Append a step to the path.  
	 * 
	 * @param x The x coordinate of the new step
	 * @param y The y coordinate of the new step
	 */
	public void appendStep(final int x, final int y, final float cost) {
		steps.add(new Step(x,y,cost));
	}
	
	/**
	 * Get the length of the path, i.e. the number of steps
	 * 
	 * @return The number of steps in this path
	 */
	public int getLength() {
		return steps.size();
	}
	
	/**
	 * Get the step at a given index in the path
	 * 
	 * @param index The index of the step to retrieve. Note this should
	 * be >= 0 and < getLength();
	 * @return The step information, the position on the map.
	 */
	public Step getStep(final int index) {
		return steps.get(index);
	}

	/**
	 * Get the x coordinate for the step at the given index
	 * 
	 * @param index The index of the step whose x coordinate should be retrieved
	 * @return The x coordinate at the step
	 */
	public int getX(final int index) {
		return getStep(index).x;
	}
	
	/**
	 * Get the y coordinate for the step at the given index
	 * 
	 * @param index The index of the step whose y coordinate should be retrieved
	 * @return The y coordinate at the step
	 */
	public int getY(final int index) {
		return getStep(index).y;
	}
	
	/**
	 * Prepend a step to the path.  
	 * 
	 * @param x The x coordinate of the new step
	 * @param y The y coordinate of the new step
	 */
	public void prependStep(final int x, final int y, final float cost) {
		steps.add(0, new Step(x, y, cost));
	}
}
