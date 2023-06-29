package gamePartition;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.IGameStats;
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

public class GameLauncher {
	private static final String SERVER_NAME = "Connect4";
    public static void main(String[] args) {
    	
    	Registry reg = null;
    	IGameStats stats = null;
    	try {
    		reg=LocateRegistry.getRegistry(1099);
    				
    	}catch(RemoteException e){
			System.err.println("No se ha localizado el rmiRegistry local");
			System.exit(-1);

    	}
    	try {
    		stats = (IGameStats)reg.lookup(SERVER_NAME);
    	}catch (Exception e) {
			System.err.println("El Follower de nombre " + SERVER_NAME + " no esta registrado");
			System.exit(-1);

		}
    	
        Board board = new Board();
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
