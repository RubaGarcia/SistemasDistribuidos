package gamePartition;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

public class gameLauncher {
	
	public static final int PUERTO = 13000;
	private static final String SERVER_NAME = "connect4";
	
    public static void main(String[] args) {
        Board board = new Board();
        ILogService logger = new LogService();
        GameStats stats = new GameStats(logger);
        
        GameControl control = null;
        try {
			control = new GameControl (board, stats);
		} catch (RemoteException e) {
			
			e.printStackTrace();
			System.exit(-1);
		}
        Registry reg = null;
        try {
        	System.out.println("creando servidor de nombres");
        	reg = LocateRegistry.createRegistry(PUERTO);
        }catch (RemoteException e) {
    		System.err.println("Ha habido un error, con el rmiRegistry");

        	e.printStackTrace();
        	System.exit(-1);
		}
           
        try {
        	reg.bind(SERVER_NAME, control);
        }catch (Exception e) {
        	e.printStackTrace();
        	System.exit(-1);
        }
        
        // CPU Player, simple or aggressive
        //IStrategy strategy = new IAStrategySimple();
        IStrategy strategy = new IAStrategyAgressive();
        new PlayerCPU(control, strategy);
        
    }
}
