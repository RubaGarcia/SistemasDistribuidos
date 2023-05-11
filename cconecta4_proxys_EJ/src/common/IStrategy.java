package common;

/**
 * IStrategy
 * 
 * Strategies followed by cpu-controlled players 
 * 
 * @author RCSD
 * @version 2.0
 */

public interface IStrategy {
	
	/**
     * Get the next move for the player
     * @param color Player's color
	 * @param gameboard Current state of the board
     * @return the column where to put the disc
     */
	public int nextMove (int color, int[][] gameboard);

    /**
     * Get the strategy's name 
     * @return the strategy's name
     */	
	public String getName();

}
