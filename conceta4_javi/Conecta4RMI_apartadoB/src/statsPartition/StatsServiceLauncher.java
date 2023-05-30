package statsPartition;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.ILogService;

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

public class StatsServiceLauncher {

	private static final int PUERTO = 1099;
	private static final String SERVER_NAME = "Connect4";

	public static void main(String[] args) {
		ILogService logger = new LogService();
		GameStats stats = null;
		try {
			stats = new GameStats(logger);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		Registry registro = null;
		try {
			registro = LocateRegistry.createRegistry(PUERTO);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			registro.bind(SERVER_NAME, stats);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Servidor iniciado");
	}
}
