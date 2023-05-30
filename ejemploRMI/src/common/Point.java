package common;

/**
 * Point.java
 * 
 * Class that represents the squares (points) in the board
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class Point {
    
    private int column; // Row in the board
    private int row;    // Column in the board
    
    public Point(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
}
