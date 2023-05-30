package concurrent;

import common.GameData;
import common.ILogService;
import common.PlayerData;

/**
 * GameStats.java
 * 
 * Management of game statistics
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public class GameStats {
  
    private ILogService logger;  		      // Logger service
    private String[] players = new String[3]; // Array of players (0 not used, no play)
    private long start_time;			      // Represents the start time for the game
    private static final long MS_IN_SECS = 1000;


    private static final int serialVersionUID = 1L;
    private static final int puerto = 12000;

    /**
     * Initializes a new instance of the stats manager.
     * @param logger the game logger for the stats
     */     
    public GameStats (ILogService logger) {
        super(puerto);
        this.logger = logger;
        showStatsInfo();//no entiendo?
    }

    /**
     * Take stats after starting the game
     */
    public void startGame () {
    	initVariables();		
    }

    /**
     * Take and log stats after finishing the game
     * @param winnerColor The color used by the winner of the game 
     */
    public void endGame(int winnerColor) {
        long stop_time = System.currentTimeMillis();
        long total_time = (stop_time - this.start_time)/MS_IN_SECS; // game duration in seconds
        int oponentColor = GameData.RED;
        if (winnerColor == GameData.RED)
        	oponentColor = GameData.BLACK;


		@SuppressWarnings("unused") // Res is not used in this version of the game
		boolean res = logger.logData(players[winnerColor], players[oponentColor], total_time);
    }
    

	/**
	 * Add player info to stats data
	 * @param player Player's data
	 */
	public void addPlayer(PlayerData player) {
		players[player.getColor()] = player.getName();
	}
	
	
	/**
	 * Shows stats info from the logging service used
	 */
	public void showStatsInfo () {
		long size = logger.getLogSize();
		System.err.println ("(DEBUG) Log size: "+size+" bytes");
	}

    /********************* 
     *** Local methods ***
     *********************/

    private void initVariables() {		
    	this.start_time = System.currentTimeMillis();
        
    }
}
