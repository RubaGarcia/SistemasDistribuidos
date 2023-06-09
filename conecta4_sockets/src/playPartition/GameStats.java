package playPartition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.GameData;
import common.PlayerData;


/**
 * GameStats.java
 * 
 * Management of game statistics
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public class GameStats {

	//private ILogService logger;  		      // Logger service
	private String[] players = new String[3]; // Array of players (0 not used, no play)
	private long start_time;			      // Represents the start time for the game
	private static final long MS_IN_SECS = 1000;


	/**
	 * Initializes a new instance of the stats manager.
	 * @param logger the game logger for the stats
	 */     
	public GameStats () {
		//this.logger = logger;
		
	}

	/**
	 * Take stats after starting the game
	 */
	public void startGame () {
		initVariables();		
		
	}

	/**
	 * Take and log stats after finishing the game
	 * @param winnerColor The color used by the winner of the game 
	 */
	public void endGame(int winnerColor) {
		long stop_time = System.currentTimeMillis();
		long total_time = (stop_time - this.start_time)/MS_IN_SECS; // game duration in seconds
		int oponentColor = GameData.RED;
		if (winnerColor == GameData.RED)
			oponentColor = GameData.BLACK;
		try {

			//boolean res = logger.logData(players[winnerColor], players[oponentColor], total_time); LLamada a logData

			Socket socket =new Socket(InetAddress.getLocalHost(),16600);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			//escribimos el stream con el identificador de que dato esta enviando
			dos.writeInt(1);//si es 1 significa que va a enviar los datos de endGame
			
			//escribimos los streams
			dos.writeUTF(players[winnerColor]);
			dos.writeUTF(players[oponentColor]);
			dos.writeLong(total_time);
			
			dos.flush();//que vac�e el stream(para optimizar)

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			@SuppressWarnings("unused") // Res is not used in this version of the game
			boolean res = dis.readBoolean();//llamada bloqueante

			//liberamos los recursos
			socket.close();//al cerrar los sockets se cierran los streams asociados


		}catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}


	/**
	 * Add player info to stats data
	 * @param player Player's data
	 */
	public void addPlayer(PlayerData player) {
		players[player.getColor()] = player.getName();
	}


	/**
	 * Shows stats info from the logging service used
	 */
	public void showStatsInfo () {
		
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(),16600);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			dos.writeInt(2);//si es 2 significa que es la funcion showStatsInfo
			
			long size = dis.readLong();//llamada bloqueante
			System.err.println("(DEBUG) Log size: "+size+" bytes");
			
			//liberamos recursos
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		//long size = logger.getLogSize();
		//System.err.println ("(DEBUG) Log size: "+size+" bytes");
	}

	/********************* 
	 *** Local methods ***
	 *********************/

	private void initVariables() {		
		this.start_time = System.currentTimeMillis();

	}
}
