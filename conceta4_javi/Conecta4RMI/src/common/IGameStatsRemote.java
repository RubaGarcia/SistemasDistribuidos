package common;

import java.rmi.Remote;

public interface IGameStatsRemote extends Remote {
	// Metodos remotos
	public void startGame() throws java.rmi.RemoteException;
	public void endGame(int winnerColor) throws java.rmi.RemoteException;
	public void addPlayer(PlayerData player) throws java.rmi.RemoteException;
}
