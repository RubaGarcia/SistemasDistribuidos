package common;

import java.rmi.Remote;

public interface IGameStats extends Remote{


    /**
     * Take stats after starting the game
     */
    public void startGame ()throws java.rmi.RemoteException;;

    /**
     * Take and log stats after finishing the game
     * @param winnerColor The color used by the winner of the game 
     */
    public void endGame(int winnerColor)throws java.rmi.RemoteException;;
    

	/**
	 * Add player info to stats data
	 * @param player Player's data
	 */
	public void addPlayer(PlayerData player)throws java.rmi.RemoteException;;
	
	
	

    
}
