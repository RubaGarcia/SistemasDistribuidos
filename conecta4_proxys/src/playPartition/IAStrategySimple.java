package playPartition;

import common.GameData;
import common.IStrategy;

/**
 * IAStrategySimple.java
 * 
 * Simple strategy followed by cpu-controlled players 
 * 
 * @author RCSD
 * @version 2.0
 */


public class IAStrategySimple implements IStrategy {
    
	/**
     * Get the next move for the player
     * @param color Player's color. Not used for this simple strategy
	 * @param gameboard Current state of the board. Not used for this simple strategy
     * @return the column where to put the disc
     */
	@Override
	public int nextMove(int color, int[][] gameboard) {
		return (int) (Math.round(Math.random()*(GameData.COLS-1)));
	}
	  
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

}
