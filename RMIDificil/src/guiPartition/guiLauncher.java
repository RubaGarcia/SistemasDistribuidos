package guiPartition;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.IGameControl;

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

public class guiLauncher {
	private static final int PUERTO = 13000;
	private static final String SERVER_NAME = "connect4";
	
    public static void main(String[] args) {
               
    	Registry reg = null;
    	IGameControl control = null;
    	
    	try {
    		reg = LocateRegistry.getRegistry(PUERTO);
    	}catch(RemoteException re) {
    		System.err.println("Ha habido un error, no se ha localizado el rmiRegistry");
    		System.exit(-1);
    	}
    	
    	try {
    		control = (IGameControl)reg.lookup(SERVER_NAME);
    	}catch (Exception e) {
    		System.err.println(SERVER_NAME + "no esta registrado");
    		e.printStackTrace();
    		System.exit(-1);
		}
        
        // Human player
        GUI gui = new GUI (control);
        new PlayerHuman("Default Player", gui, control);
    }
}
