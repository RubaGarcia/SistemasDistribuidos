package logPartition;

import common.ILogService;
//import common.IStrategy;

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

public class Log4Launcher {

    public static void main(String[] args) {
        ILogService logger = new LogService();
        ProxyServer prxS = new ProxyServer(logger);       
        
    }
}
