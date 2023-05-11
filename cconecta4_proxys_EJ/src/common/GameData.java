package common;

/**
 * GameData.java
 * 
 * Game configuration parameters
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public class GameData {

    /**
     * Represents the size of the disc.
     */
    public static final int DISC_SIZE = 75;
    
    /**
     * Represents the size of the tile.
     */
    public static final int TILE_SIZE = 100;
    
    /**
     * Represents the distance between the game board and the window.
     */
    public static final int MARGIN = 50;
    
    /**
     * Represents the font size of the text drawn in the win sequence.
     */
    public static final int FONT_SIZE = 48;
    
    /**
     * Represents the status of a tile in the game board. In this case: an empty tile.
     */
    public static final int EMPTY = 0;
    
    /**
     * Represents the status of a tile in the game board. In this case: A red disc.
     */
    public static final int RED = 1;
    
    /**
     * Represents the status of a tile in the game board. In this case: A black disc.
     */
    public static final int BLACK = 2;
    
    /**
     * Represents the # of rows in the game board.
     */
    public static final int ROWS = 6;
    
    /**
     * Represents the # of columns in the game board.
     */
    public static final int COLS = 7;
    
    /**
     * Represents the speed in which the timer fires for the dropping disc.
     */
    public static final int Y_DISC_VELOCITY = 10;

}
