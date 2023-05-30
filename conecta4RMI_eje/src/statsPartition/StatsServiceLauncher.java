import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.ILogService;
import concurrent.GameStats;
import logPartition.LogService;

public class StatsServiceLauncher {

    private static final int PUERTO = 1099;
	private static final String SERVER_NAME = "Connect4";


    public void main(String[] args){
   
        ILogService logger = new LogService();
        GameStats stats = null;
 
        try {
            stats = new GameStats(logger);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Registry reg = null;

        try {
            reg = LocateRegistry.getRegistry(PUERTO);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            reg.bind(SERVER_NAME,stats);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);

        }

        System.out.println("movidas arrancadas");

    }
}
