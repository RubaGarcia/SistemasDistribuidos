package playPartition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import common.Disc;
import common.GameData;
import common.PlayerData;
import common.Point;

/**
 * GameControl.java
 * 
 * Class that represents the controller of the game.
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class GameControl implements ActionListener {

    /**
     * Represents the model or the data for the game.
     */
    private final Board board;
    
    /**
     * Represents the statistics for the game.
     */    
    private final GameStats gameStats;

    /**
     * Represents the number of players for the game.
     */
    private int playerNumber = 0;
    
    /**
     * Represents timer for the dropping disc.
     */
    private Timer timer;
    
    /**
     * Represents the location where the user clicks.
     */
    private Point clickPoint;
    
    /**
     * Initializes a new instance of the game controller.
     * @param board the board to control
     * @param gameStats the stats for the game
     */
    public GameControl(Board board, GameStats gameStats) {
        this.board = board;
        this.gameStats = gameStats;
        timer = new Timer(0, null);
        clickPoint = new Point(0,0);
    }
       
    /**
     * Move a disc within the board
     * @param point Point with the desired column
     * @param color Player's color for the movement
     */
    public void moveDisc (Point point, int color) {
        int column = point.getColumn();
        int row = point.getRow();

        // If not player's turn...return.
        if (color != board.getCurrentColor()) return;
        
        // If a disc is currently falling...return.
        if (board.isDiscMoving()) return;

        // If the user clicks outside the game board...return.
        if (column < 0 || column >= GameData.COLS) return;

        // If in the win sequence...return.
        if (board.isWinnerFound()) return;

        // If board is full.... restart game
        boolean fullBoard = true;
        for (int i = 0; i < GameData.COLS; i++) {
            if (board.getGameBoard()[0][i] == GameData.EMPTY) {
                fullBoard = false;
            }
        }
        if (fullBoard) {
            restart();
            return;
        }
        
        // If the user clicks a full column...return.
        if (board.getGameBoard()[0][column] != GameData.EMPTY) return;    
        
        // Updates the location of where the user clicked.
        this.setClickPoint(new Point (row, column)); 
        Disc droppingDisc = setupDroppingDisc();

        // Start the timer.
        timer.start();
        droppingDisc.startsMoving();
    }
    

    /**
     * Register a player in the game.
     * @param name Name of the player
     * @return the color that represents the player during the game
     */
    public int registerPlayer(String name) {
        int playerColor = GameData.EMPTY; // No play
        
        playerNumber++;
        if (playerNumber == 1) {
        	playerColor = GameData.RED;
        	PlayerData player = new PlayerData (playerColor, name);
        	gameStats.addPlayer(player);        	
        }
        else if (playerNumber == 2) {
        	playerColor = GameData.BLACK;
        	PlayerData player = new PlayerData (playerColor, name);
        	gameStats.addPlayer(player);
        	gameStats.startGame();
        }

        return playerColor;
    }
    
    /**
     * Obtain the color currently playing
     * @return the color
     */
    public int playingColor() {
        return board.getCurrentColor();
    }

    /**
     * Called every time the timer fires. Updates the location of the dropping
     * disc and tells the view to redraw the screen. Stops the timer once
     * it has reached it's stopping y-coordinate and checks for Connect-Fours
     * otherwise it switches player turns.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Increment Y-coordinate of falling disc.
        Disc droppingDisc = board.getDroppingDisc();
        droppingDisc.setY(droppingDisc.getY()+10);

        //Check if dropping disc has reached bottom of game board.
        if (droppingDisc.getY() >= droppingDisc.getStopY() + GameData.DISC_SIZE) {
            int row = droppingDisc.getStopY() / GameData.TILE_SIZE;
            int col = this.getClickPoint().getColumn();
            //Place a disc where the falling disc landed.
            Point newPoint = new Point (row, col);
            board.setGameBoard(newPoint);
            //Stop the timer.
            timer.stop();
            droppingDisc.stopsMoving();

            //Check if a Connect-Four is found, else switch turns.
            if (checkWin() > 0) {
                board.setWinner(true);
                int winner = board.getCurrentColor();
                gameStats.endGame(winner);
            }
            
            else
                switchColor();
        }
    }
    
	/**
	 * Obtain the color currently playing
	 * @return the color currently playing
	 */
	public int getCurrentColor() {
		return board.getCurrentColor();
	}

	/**
	 * Obtain if disc animation is in progress
	 * @return true if disc is moving, false otherwise
	 */
	public boolean isDiscMoving() {
		return board.isDiscMoving();
	}

	/**
     * Obtain the current dropping disc
     * @return the dropping disc for the ongoing turn
	 */
	public Disc getDroppingDisc() {
		return board.getDroppingDisc();
	}

	/**
     * Indicate if there is already a winner for the game
     * @return true if there is a winner, false otherwise
	 */
	public boolean isWinnerFound() {
		return board.isWinnerFound();
	}

	/**
     * Obtain the current state of the board for the ongoing game.
     * @return an array representing the board
	 */
	public int[][] getGameBoard() {
		return board.getGameBoard();
	}

	/**
     * Obtain the location of 4 discs which make up the Connect-Four
     * @return the location of 4 discs which make up the Connect-Four
	 */
	public Point[] getConnectFour() {
		return board.getConnectFour();
	}
	
    
    /**********************
     *** Game internals ***
     **********************/
    
    /**
     * Restarts the game.
     */
    private void restart() {
        board.reset();
    }

    /**
     * Responsible for preparing the dropping disc for dropping.
     * Sets the location where it should be dropped and where it should stop dropping.
     */
    private Disc setupDroppingDisc() {
        Disc droppingDisc = board.getDroppingDisc();
        Point clickPoint = this.getClickPoint();
        
        //Setup the timer.
        timer = new Timer(droppingDisc.getYVelocity(), this);
        //Set the X-Coordinate of disc as the clicked column
        droppingDisc.setX(clickPoint.getColumn() * GameData.TILE_SIZE);
        //Set Y-Coordinate as top of screen.
        droppingDisc.setY(0);

        //Step through each row of the clicked column.
        //Find the first empty spot and set the location of the dropping disc to stop there.
        for (int i = GameData.ROWS - 1; i >= 0; i--) {
            if (board.getGameBoard()[i][clickPoint.getColumn()] == GameData.EMPTY) {
                droppingDisc.setStopY(i * GameData.TILE_SIZE);
                break;
            }
        }
        return droppingDisc;
    }
    
    /**
     * Switches the current color or turn.
     */
    private void switchColor() {
        int currentColor = board.getCurrentColor();
        int nextColor = GameData.RED;
        
        if (currentColor == GameData.RED) {
            nextColor = GameData.BLACK;
        } 
        board.setCurrentColor(nextColor);
    }

    /**
     * Checks for Connect-Fours. Accomplishes this by comparing every possible
     * starting location for a Connect-Four with its succeeding discs.
     * @return The color of the connect four or 0 if none was found.
     */
    private int checkWin() {
        int start = 0;
        Point [] winnerSequence = new Point[4];
        int[][] currentBoard = board.getGameBoard();

        //Check horizontal win
        for (int row = GameData.ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < GameData.COLS - 3; col++) {
                start = currentBoard[row][col];
                if (start != GameData.EMPTY 
                    && start == currentBoard[row][col + 1]
                        && start == currentBoard[row][col + 2]
                            && start == currentBoard[row][col + 3]) {
                    for (int i = 0; i < 4; i++) {					
                        winnerSequence[i] = new Point(row, col + i);
                    }
                    board.setConnectFour (winnerSequence);
                    return start;
                }
            }
        }

        //Check vertical win
        for (int row = GameData.ROWS - 1; row >= 3; row--) {
            for (int col = 0; col < GameData.COLS; col++) {
                start = currentBoard[row][col];
                if (start != GameData.EMPTY 
                    && start == currentBoard[row - 1][col]
                        && start == currentBoard[row - 2][col]
                            && start == currentBoard[row - 3][col]) {
                    for (int i = 0; i < 4; i++) {
                        winnerSequence[i] = new Point(row - i, col);
                    }
                    board.setConnectFour (winnerSequence);
                    return start;
                }
            }
        }

        //Check diagonal win from bottom left to top right
        for (int row = GameData.ROWS - 1; row >= 3; row--) {
            for (int col = 0; col < GameData.COLS - 3; col++) {
                start = currentBoard[row][col];
                if (start != GameData.EMPTY 
                    && start == currentBoard[row - 1][col + 1]
                        && start == currentBoard[row - 2][col + 2]
                            && start == currentBoard[row - 3][col + 3]) {
                    for (int i = 0; i < 4; i++) {
                        winnerSequence[i] = new Point(row - i, col + i);
                    }
                    board.setConnectFour (winnerSequence);
                    return start;
                }
            }
        }

        //Check diagonal win from bottom right to top left
        for (int row = GameData.ROWS - 1; row >= 3; row--) {
            for (int col = GameData.COLS - 1; col >= 3; col--) {
                start = currentBoard[row][col];
                if (start != GameData.EMPTY 
                    && start == currentBoard[row-1][col-1]
                        && start == currentBoard[row-2][col-2]
                            && start == currentBoard[row-3][col-3]) {
                    for (int i = 0; i < 4; i++) {
                        winnerSequence[i] = new Point(row - i, col - i);
                    }
                    board.setConnectFour (winnerSequence);
                    return start;
                }
            }
        }
        
        return 0;
    }
    
    /**
     * Obtain the mouse's click point for the player
     * @return the mouse's click point
     */
    private synchronized Point getClickPoint() {
        return clickPoint;
    }

    /**
     * Set the mouse's click point for the player
     * @param point mouse's click point
     */
    private synchronized void setClickPoint(Point point) {
        this.clickPoint = point;
    }

}
