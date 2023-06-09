package logPartition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import common.ILogService;

/**
 * LogService.java
 * 
 * ILogging service based on text file
 * 
 * Remake of a Connect-4 strategy game based on version authored by Andrew Zhao
 * 
 * @author RCSD
 * @version 2.0
 */

public class LogService extends Thread implements ILogService {

	private static final String fileName = "connect4.log"; // Name of the log file
	private File file   = null;							   // Records file


	/**
	 * Initializes a new instance of the log service based on a text file
	 */ 
	public LogService() {
		file = new File(fileName);
		this.start();
	}

	/**
	 * Log game data
	 * @param winner  Winner's name 
	 * @param opponent  Opponent's name 
	 * @param score Winner's score in the game
	 * @return true if data has been successfully logged, false otherwise 
	 */
	@Override
	public boolean logData (String winner, String opponent, long score) {
		boolean returnData = true;
		try {
			if (!file.exists()) {
				file.createNewFile(); // create file if it does not exist
			}
			FileWriter writer = new FileWriter(file, true); // Append data
			writer.write(winner+"(w) Vs. "+opponent+": "+score+System.getProperty( "line.separator" ));
			writer.close();
		} catch (IOException e) {
			// For simplicity, remain silent about it
			returnData = false; // Data is not logged
		}
		return returnData;
	}

	/**
	 * Retrieve the size of the log
	 * @return the size of the log in bytes
	 */	
	@Override
	public long getLogSize () {
		return file.length(); // Returns 0 if file does not exist
	}


	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(16600);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		while(!Thread.interrupted()) {
			try{

				Socket client = server.accept();
				//tenemos ya conexion con el socket del cliente

				DataInputStream dis = new DataInputStream(client.getInputStream());
				DataOutputStream dos = new DataOutputStream(client.getOutputStream());
				if(dis.readInt()==1) {
					String winner = dis.readUTF();
					String opponent = dis.readUTF();
					long score = dis.readLong();

					Boolean res = this.logData(winner, opponent, score);
					dos.writeBoolean(res);
				} else {
					long size = getLogSize();
					dos.writeLong(size);
				}



				//liberamos recursos
				client.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

		}
		try {
			server.close();
		} catch (IOException e) {

			e.printStackTrace();
			System.exit(-1);
		}


	}
}
