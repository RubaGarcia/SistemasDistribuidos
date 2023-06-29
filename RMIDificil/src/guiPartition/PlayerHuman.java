package guiPartition;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

import common.GameData;
import common.IGameControl;
import common.Point;

/**
 * PlayerHuman.java
 * 
 * Class that represents a player controlled by the human.
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class PlayerHuman {

	private GUI gui;                        // GUI for the player
	//private GameControl control;            // Controller for the game
	private int color;                      // Color for playing the game

	IGameControl control;	//interfaz de gameControl

	/**
	 * Initializes a new instance of the human player
	 * @param name the name for the player
	 * @param gui the GUI for the player
	 * @param control the game controller
	 */ 
	public PlayerHuman(String name, GUI gui, IGameControl control) {
		this.gui = gui;
		this.control = control;

		try {
			this.color = control.registerPlayer(name);
		} catch (RemoteException e) {
			
			e.printStackTrace();
			System.exit(-1);
		}


		// Add listener to the windows' game
		this.gui.addListener(new PanelListener());
		this.gui.addListener(new CursorListener());
	}

	/**
	 * Nested class to handle user clicks in the GUI 
	 */
	class PanelListener implements MouseListener {

		@Override
		/**
		 * Retrieves and updates user clicks.
		 */
		public void mousePressed(MouseEvent e) {

			//If the user clicks outside the game board...return.
			if (e.getX() < GameData.MARGIN 
					|| e.getX() > GameData.MARGIN + GameData.COLS * GameData.TILE_SIZE) return;

			//Retrieves the location of where the user clicked.
			int row = (int) Math.floor((e.getY() - GameData.MARGIN) / GameData.TILE_SIZE);
			int column = (int) Math.floor((e.getX() - GameData.MARGIN) / GameData.TILE_SIZE);

			Point newPoint = null;
			newPoint = new Point (row, column);
			try {
				control.moveDisc(newPoint, color);

			}catch(RemoteException re) {
				re.printStackTrace();
				System.exit(-1);
			}

		}

		/**
		 * Methods required to implement by MouseListener.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}  
	}

	/**
	 * Nested class to handle mouse pointer in the GUI 
	 */
	class CursorListener implements MouseMotionListener {

		/**
		 * Retrieves and updates the location of the user's mouse.
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			int playingColor = -1;
			try {
				playingColor = control.playingColor();
			} catch (RemoteException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
			if (color == playingColor) {
				int column = e.getX();
				Point mousePoint=null;
				mousePoint = new Point (0, column);
				gui.setMousePoint(mousePoint);
			}
		}

		/**
		 * Methods required implement by MouseMotionListener.
		 */
		@Override
		public void mouseDragged(MouseEvent e) {}
	}

}
