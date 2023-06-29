package gamePartition;

import common.GameData;
import common.IStrategy;

/**
 * IAStrategyAgressive.java
 * 
 * Agressive strategy followed by cpu-controlled players 
 * 
 * @author RCSD
 * @version 2.0
 */

public class IAStrategyAgressive implements IStrategy {
	/**
     * Get the next move for the player
     * @param color Player's color
	 * @param gameboard Current state of the board
     * @return the column where to put the disc
     */
	@Override
    public int nextMove(int color, int[][] gameboard) {

        // Retrieve board info
        int[] columnsHeight = this.getColumnsHeight(gameboard);

        // Get playing colors
        int opponent = GameData.BLACK;
        if (color == GameData.BLACK)
            opponent = GameData.RED;

        // Reset matrix for storing movement values
        int[] valueMatrix = new int[GameData.COLS];
        for (int i = 0; i < valueMatrix.length; i++) 
            valueMatrix[i] = 0;

        // Fill decision matrix
        for (int i = 0; i < valueMatrix.length; i++) {
            // column is full
            if (columnsHeight[i] == GameData.ROWS) { 
                valueMatrix[i] = 0;
                continue;
            }
            // Except if column is full, we need to evaluate each possible move
            int col = i;
            int row = GameData.ROWS - columnsHeight[i] - 1;
            
            int attack_score = checkWinnerSequences(gameboard, row, col, color, color);
            int defense_score = checkWinnerSequences(gameboard, row, col, color, opponent);
            valueMatrix[i] = attack_score + defense_score;            
        }
        
        // Retrieve the maximum value from decision matrix
        int max = 0;
        int column = (int) (Math.round(Math.random()*(GameData.COLS-1))); // Random column if matrix is full of zeros
        for (int i = 0; i < 7; i++) {
            if (valueMatrix[i] > max) {
                max = valueMatrix[i]; 
                column = i;
            }
        }
        
        return column;
    }
	
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
      
    /**********************
     *** Game internals ***
     **********************/

    /**
     * Get the current height of each column
     * @param gameboard board to analyze
     * @return array with the height of the columns (0-> column is empty, 6-> column is full)
     */
    private int [] getColumnsHeight (int[][] gameboard) {
        int[] columns= new int[GameData.COLS];
        for (int column = 0; column < columns.length; column++) { 
            int level = 0;
            for (int row = 0; row < GameData.ROWS; row++) {
                if (gameboard [row][column] != GameData.EMPTY)
                    level++;
            }
            columns[column] = level;
        }       
        return columns;
    }

    /**
     * Check if there is any winner sequence in the game
     * @param gameboard Current state of the board
     * @param row Row to move the disc
     * @param col Col to move the disc
     * @param myColor Color of this CPU player
     * @param playerColor Color of the player to analyze
     * @return a value representing the score obtained by this movement
     */
    private int checkWinnerSequences (int[][] gameboard, int row, int col, int myColor, int playerColor) {
        int weigth = 0;
        int max_weigth = 16;
        final int medium_weight = 4;
        final int min_weigth = 2;

        // Preference for defensive than for aggressive strategy
        if (playerColor == myColor) {
            max_weigth = max_weigth / 2;
        }

        // Check for three consecutive discs
        // Horizontal winner sequence (R->L)
        if ((col>=3) 
            && (gameboard[row][col-1] == playerColor)
            && (gameboard[row][col-2] == playerColor)
            && (gameboard[row][col-3] == playerColor))
            weigth = weigth + max_weigth;     
        // Horizontal winner sequence (L->R)
        if ((col<=3) 
            && (gameboard[row][col+1] == playerColor)
            && (gameboard[row][col+2] == playerColor)
            && (gameboard[row][col+3] == playerColor))
            weigth = weigth + max_weigth;
        
        // Vertical winner sequence (B->T)
        if ((row<=2) 
            && (gameboard[row+1][col] == playerColor)
            && (gameboard[row+2][col] == playerColor)
            && (gameboard[row+3][col] == playerColor))
            weigth = weigth + max_weigth;           
        
        // Set of Diagonal winner sequences
        if ((col>=3) && (row<=2)
            && (gameboard[row+1][col-1] == playerColor)
            && (gameboard[row+2][col-2] == playerColor)
            && (gameboard[row+3][col-3] == playerColor))
            weigth = weigth + max_weigth;
        if ((col<=3) && (row<=2)
            && (gameboard[row+1][col+1] == playerColor)
            && (gameboard[row+2][col+2] == playerColor)
            && (gameboard[row+3][col+3] == playerColor))
            weigth = weigth + max_weigth;
        if ((col>=3) && (row>=3)
            && (gameboard[row-1][col-1] == playerColor)
            && (gameboard[row-2][col-2] == playerColor)
            && (gameboard[row-3][col-3] == playerColor))
            weigth = weigth + max_weigth;
        if ((col<=3) && (row>=3)
            && (gameboard[row-1][col+1] == playerColor)
            && (gameboard[row-2][col+2] == playerColor)
            && (gameboard[row-3][col+3] == playerColor))
            weigth = weigth + max_weigth;

        // Check for two consecutive discs
        // Horizontal sequence (R->L)
        if ((col>=2) 
            && (gameboard[row][col-1] == playerColor)
            && (gameboard[row][col-2] == playerColor))
            weigth = weigth + medium_weight;
        // Horizontal sequence (L->R)
        if ((col<=4) 
            && (gameboard[row][col+1] == playerColor)
            && (gameboard[row][col+2] == playerColor))
            weigth = weigth + medium_weight;
        
        // Vertical sequence (B->T)
        if ((row<=3) 
            && (gameboard[row+1][col] == playerColor)
            && (gameboard[row+2][col] == playerColor))
            weigth = weigth + medium_weight;
        
        // Set of Diagonal sequences
        if ((col>=2) && (row<=3)
            && (gameboard[row+1][col-1] == playerColor)
            && (gameboard[row+2][col-2] == playerColor))
            weigth = weigth + medium_weight;
        if ((col<=4) && (row<=3)
            && (gameboard[row+1][col+1] == playerColor)
            && (gameboard[row+2][col+2] == playerColor))
            weigth = weigth + medium_weight;
        if ((col>=2) && (row>=2)
            && (gameboard[row-1][col-1] == playerColor)
            && (gameboard[row-2][col-2] == playerColor))
            weigth=weigth + medium_weight;
        if ((col<=4) && (row>=2)
            && (gameboard[row-1][col+1] == playerColor)
            && (gameboard[row-2][col+2] == playerColor))
            weigth = weigth + medium_weight;

        // Check for one consecutive discs
        // Horizontal sequence (R->L)
        if ((col>=1) 
            && (gameboard[row][col-1] == playerColor))
            weigth = weigth + min_weigth;
        // Horizontal sequence (L->R)
        if ((col<=5) 
            && (gameboard[row][col+1] == playerColor))
            weigth = weigth + min_weigth;
        
        // Vertical sequence (B->T)
        if ((row<=4) 
            && (gameboard[row+1][col] == playerColor))
            weigth = weigth + min_weigth;
        
        // Set of Diagonal sequences
        if ((col>=1) && (row<=4)
            && (gameboard[row+1][col-1] == playerColor))
            weigth = weigth + min_weigth;

        if ((col<=5) && (row<=4)
            && (gameboard[row+1][col+1] == playerColor))
            weigth = weigth + min_weigth;

        if ((col>=1) && (row>=1)
            && (gameboard[row-1][col-1] == playerColor))
            weigth = weigth + min_weigth;

        if ((col<=5) && (row>=1)
            && (gameboard[row-1][col+1] == playerColor))
            weigth=weigth + min_weigth;

        return weigth;
    }

}
