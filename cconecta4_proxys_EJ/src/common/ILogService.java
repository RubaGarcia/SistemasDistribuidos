package common;

/**
 * ILogService.java
 * 
 * Logging services for the game
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public interface ILogService {

    /**
     * Log game data
     * @param winner  Winner's name 
     * @param opponent  Opponent's name 
     * @param score Winner's score in the game
     * @return true if data has been successfully logged, false otherwise 
     */
    public boolean logData (String winner, String opponent, long score);
    
    /**
     * Retrieve the size of the log
     * @return the size of the log in bytes
     */
    public long getLogSize ();

}
