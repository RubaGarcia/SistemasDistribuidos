package common;

import java.rmi.Remote;

public interface IGameControl extends Remote{

       
    /**
     * Move a disc within the board
     * @param point Point with the desired column
     * @param color Player's color for the movement
     */
    public void moveDisc (Point point, int color) throws java.rmi.RemoteException;
     
    

    /**
     * Register a player in the game.
     * @param name Name of the player
     * @return the color that represents the player during the game
     */
    public int registerPlayer(String name)throws java.rmi.RemoteException;
    
    /**
     * Obtain the color currently playing
     * @return the color
     */
    public int playingColor()throws java.rmi.RemoteException;

    
	/**
	 * Obtain the color currently playing
	 * @return the color currently playing
	 */
	public int getCurrentColor()throws java.rmi.RemoteException;

	/**
	 * Obtain if disc animation is in progress
	 * @return true if disc is moving, false otherwise
	 */
	public boolean isDiscMoving()throws java.rmi.RemoteException;

	/**
     * Obtain the current dropping disc
     * @return the dropping disc for the ongoing turn
	 */
	public Disc getDroppingDisc()throws java.rmi.RemoteException;

	/**
     * Indicate if there is already a winner for the game
     * @return true if there is a winner, false otherwise
	 */
	public boolean isWinnerFound()throws java.rmi.RemoteException;

	/**
     * Obtain the current state of the board for the ongoing game.
     * @return an array representing the board
	 */
	public int[][] getGameBoard()throws java.rmi.RemoteException;

	/**
     * Obtain the location of 4 discs which make up the Connect-Four
     * @return the location of 4 discs which make up the Connect-Four
	 */
	public Point[] getConnectFour()throws java.rmi.RemoteException;
    
    /**********************
     *** Game internals ***
     **********************/
    

    
    

}
