package playPartition;

/**
 * Board.java
 * 
 * Class that represents the board (model) of the game.
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */


import common.Disc;
import common.GameData;
import common.Point;

/**
 * Board.java
 * 
 * Class that represents the board of the game.
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class Board {

    /**
     * Represents the gameBoard in which discs are placed.
     */
    private int[][] gameBoard;

    /**
     * Represents the disc which drops when the user clicks a column of the game board.
     */
    private Disc droppingDisc;

    /**
     * Represents the location of 4 discs which make up the Connect-Four.
     */
    private Point[] connectFour;

    /**
     * Represents whether or not to begin drawing the win Sequence.
     */
    private boolean winnerFound;

    /**
     * Represents the color of the current turn.
     */
    private int currentColor = GameData.RED;

    /**
     * Initializes a new instance of Board with default values.
     */
    public Board() {
        gameBoard = new int[GameData.ROWS][GameData.COLS];
        connectFour = new Point[4];
        
        initGame();
    }

    /**
     * Obtain the current state of the board for the ongoing game.
     * @return an array representing the board
     */
    public int[][] getGameBoard() {
        return this.gameBoard;
    }

    /**
     * Sets a disc with the current playing color in the game board
     * @param point place to locate the disc
     */
    public void setGameBoard(Point point) {
        this.gameBoard[point.getRow()][point.getColumn()] = currentColor;
    }

    /**
     * Obtain the current playing color
     * @return the color for the ongoing turn
     */
    public int getCurrentColor() {
        return currentColor;
    }

    /**
     * Set the current playing color
     * @param color the color for the ongoing turn
     */
    public void setCurrentColor(int color) {
        this.currentColor = color;
    }

    /**
     * Obtain the current dropping disc
     * @return the dropping disc for the ongoing turn
     */
    public Disc getDroppingDisc() {
        return droppingDisc;
    }

    /**
     * Indicate if there is any dropping disc
     * @return true if the disc is falling
     */
    public boolean isDiscMoving() {
        return droppingDisc.getMoving();
    }

    /**
     * Indicate if there is already a winner for the game
     * @return true if there is a winner, false otherwise
     */
    public boolean isWinnerFound() {
        return winnerFound;
    }

    /**
     * Set winner for the game
     * @param winnerFound boolean value to indicate if there is a winner 
     * for the ongoing game
     */
    public void setWinner(boolean winnerFound) {
        this.winnerFound = winnerFound;
    }

    /**
     * Obtain the location of 4 discs which make up the Connect-Four
     * @return the location of 4 discs which make up the Connect-Four
     */
    public Point[] getConnectFour() {
        return connectFour.clone();
    }

    /**
     * Set the location of 4 discs which make up the Connect-Four
     * @param winnerSequence the location of 4 discs which make up the Connect-Four
     */
    public void setConnectFour(Point[] winnerSequence) {
        this.connectFour = winnerSequence;
    }

    /**
     * Reset game variables to default
     */
    public void reset() {
        initGame();
    }

    private void initGame () {
        // Reset game board
        for (int i = 0; i < GameData.ROWS; i++) {
            for (int j = 0; j < GameData.COLS; j++) {
                gameBoard[i][j] = GameData.EMPTY;
            }
        }

        // Reset connect four winner sequence
        for (int i = 0; i < 4; i++) {
            this.connectFour[i]= new Point(0,0); 
        }
        
        // Reset other internal variables
        droppingDisc = new Disc(0, 0, 0, GameData.Y_DISC_VELOCITY);
        winnerFound = false;
        
        // sets the current turn to red by default
        currentColor = GameData.RED;
    }
}
