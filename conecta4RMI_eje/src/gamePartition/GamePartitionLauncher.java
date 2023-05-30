package gamePartition;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.IGameStatsRemote;
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

    private static final int PUERTO = 1099;
	private static final String SERVER_NAME = "Connect4";


    public static void main(String[] args) {

        Registry reg = null;
        IGameStatsRemote gameStats = null;


        try {
            reg = LocateRegistry.getRegistry(PUERTO);
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(-1);
        }

        try {
            gameStats = (GameStats) registry.lookup(SERVER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }


        Board board = new Board();

        GameStats stats = null;
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
