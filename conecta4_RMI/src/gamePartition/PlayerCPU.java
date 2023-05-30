package gamePartition;

import java.rmi.RemoteException;

import common.IStrategy;
import common.Point;

/**
 * PlayerCPU.java
 * 
 * Class that represents a player controlled by the CPU.
 * This player is a simplified version
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class PlayerCPU extends Thread {

	private GameControl control;            // Controller for the game
	private static final int PERIOD = 500;  // Delay between cpu movements
	private int color;                      // Color for playing the game
	private IStrategy strategy;				// Strategy used by cpu player


    /**
     * Initializes a new instance of the cpu player
     * @param control the game controller
     * @param strategy the game strategy for the player
     */ 
	public PlayerCPU(GameControl control, IStrategy strategy) {
		this.control = control;
		String name = "Default_CPU_Player";
		this.color = control.registerPlayer(name);
		this.strategy = strategy;

		this.start();
	}

	@Override
	public void run() {
		final int row = 0; // Only the column matters to move discs within the board

		// Main loop
		while (!this.isInterrupted()) {
			int playing_color = control.playingColor();
			if (playing_color == color) {
				int[][] current_board = control.getGameBoard();
				int column = strategy.nextMove(color, current_board);
				Point newPoint=null;
				try {
					newPoint = new Point(row, column);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				control.moveDisc(newPoint, color);
			}
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				this.interrupt();
			} 
		}
	}
}
