package gamePartition;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import common.Disc;
import common.GameData;
import common.Point;
import view.BoardView;

/**
 * GUI.java
 * 
 * Class that represents the graphical user inteface for the game.
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class GUI extends JFrame implements Runnable {

    /**
     * Version 1.0 of this game
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the view or what the user sees.
     */
    private final BoardView view;

    /**
     * Represents the data for the game.
     */
    private final GameControl game;

    /**
     * Represents the location of the mouse cursor.
     */
    private Point mousePoint;

    /**
     * Represents the GUI update interval.
     */
    private final int PERIOD_IN_MS = 5;

    
    /**
     * Initializes a new instance of the graphical user interface.
     * @param game the game controller
     */   
    public GUI (GameControl game) {
        view = new BoardView ();
        this.game = game;   
        setupFrame();

        //Setup mouse pointer for disc animation
        mousePoint = new Point(0,0);

        this.addWindowListener(new CloseListener());
        Panel window = new Panel ();
        this.add(window);
        this.setVisible(true);

        new Thread (this).start(); // Start GUI activity
    }

    // GUI activity
    @Override
    public void run() {
        Thread guiThread = Thread.currentThread();
        // Main loop
        while(!guiThread.isInterrupted()){
            try {
                updateGraphics();
                Thread.sleep(PERIOD_IN_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } 
    }

    /**
     * Add listener for the GUI elements
     * @param listener listener to handle GUI events
     * 
     */
    public void addListener(EventListener listener) {
        if (listener instanceof MouseListener) {
            this.addMouseListener((MouseListener) listener);
        }
        else if (listener instanceof MouseMotionListener) {
            this.addMouseMotionListener((MouseMotionListener) listener);
        }
    }

    /**
     * Finish the resources associated with the game.
     */
    public void endGame() {
        System.out.println("Terminando el juego...");
    }

    /**
     * Set the mouse's pointer position for the player
     * @param point the mouse's pointer position
     */
    public void setMousePoint(Point point) {
        this.mousePoint = point;
    }

    /**
     * Set listener for exit.
     * Called when the windows is visible
     */
    public class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            // Finish game
            endGame();
        }
    }


    /**********************
     *** Game internals ***
     **********************/

    class Panel extends JPanel {

        /**
         * Version 1.0 of this game
         */
        private static final long serialVersionUID = 1L;

        /**
         * Responsible for updating the view.
         * Draws the dropping disc and then draws the game board
         * to simulate the disc dropping inside the game board.
         * Draws a disc which follows the user's cursor if no disc is dropping.
         * Draws the win sequence when a Connect Four is found. 
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Updates color of dropping disc to be the current players color
            int playColor = game.getCurrentColor();
            view.setPlayColor (playColor);

            // Updates drawing: disc falling or at top
            if (game.isDiscMoving()) {
                //Draw falling disc
                Disc disc = game.getDroppingDisc();
                view.drawDiscFalling (g, disc);
            } else {
                //Draw disc that follows mouse cursor at top
                if (!game.isWinnerFound()) {
                    int column = mousePoint.getColumn();
                    view.drawDiscMoving (g, column);
                }
            }

            // Updates drawing: current board
            int [][] currentBoard = game.getGameBoard();
            for (int i = 0; i < GameData.ROWS; i++) {
                for (int j = 0; j < GameData.COLS; j++) {
                    view.drawBoardElement(g, i, j, currentBoard[i][j]);
                }
            }

            // Updates drawing:  winner sequence
            if (game.isWinnerFound()) {
                Point[] winner = game.getConnectFour();
                view.drawWinSequence(g, winner, playColor);
            }
        }
    }

    /**
     * Update GUI elements
     */
    private void updateGraphics() {       
        this.repaint();
    }

    /**
     * Setup GUI frame
     */
    private void setupFrame() {
        int frameWidth = 2 * GameData.MARGIN + GameData.COLS * GameData.TILE_SIZE;
        int frameHeight = 3 * GameData.MARGIN + GameData.ROWS * GameData.TILE_SIZE;

        this.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
        this.setTitle("Connect Four");
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);    
    }

}
