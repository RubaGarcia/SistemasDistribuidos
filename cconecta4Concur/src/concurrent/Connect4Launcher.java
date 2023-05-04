package concurrent;

import common.ILogService;
import common.IStrategy;

/**
 * Connect4Launcher.java
 * 
 * Class that represents the launcher of the app
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD 
 * @version 2.0
 */

public class Connect4Launcher {

    public static void main(String[] args) {
        Board board = new Board();
        ILogService logger = new LogService();
        GameStats stats = new GameStats(logger);
        GameControl control = new GameControl (board, stats);
               
        // CPU Player, simple or aggressive
        //IStrategy strategy = new IAStrategySimple();
        IStrategy strategy = new IAStrategyAgressive();
        new PlayerCPU(control, strategy);
        
        // Human player
        GUI gui = new GUI (control);
        new PlayerHuman("Default Player", gui, control);
    }
}
