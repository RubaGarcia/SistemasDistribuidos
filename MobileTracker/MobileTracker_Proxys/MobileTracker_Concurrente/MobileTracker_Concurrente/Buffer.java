/**********************************************************************************
 *   ********** Computer and Real-Time Group . University of Cantabria **********
 *                       *** MobileTracker Projects ***   
 *   
 *   Clase protegida: Buffer
 *   Buffer utilizado para la transmidi�n as�ncrona entre le movil que opera
 *   periodicamente y el follower que opera espor�dicamente.
 *   
 *    Author: J.M. Drake
 *    Version: 04-05-2014
 *
 **********************************************************************************/
package mobileTrackerConcurrente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class Buffer {
	private final int BUFFER_SIZE = 20; // Tamanho maximo del buffer
	private LinkedList<Position> position = new LinkedList<Position>();

	private static final String FOLLOWER_IP="127.0.0.1";
	private static final int PORT=12000;
	private InetAddress SERVER_ADDRESS;

	public Buffer(){
		try{
			SERVER_ADDRESS=InetAddress.getByName(FOLLOWER_IP);
		}catch(UnknnownHostException e){
			System.out.println("la movida está mal(computador desconocido)");
		}
	}


	/**
	 * Introduce en el buffer una posición
	 * @params datos de la posición
	 * @return false si el buffer esta lleno, true si la posición es almacenada
	 */
	public synchronized boolean putPosition(long time,double x,double y,double z){
		Socket server = null;
		boolean returnData=false;
		try{

			server = new Socket(SERVER_ADDRESS, PORT);
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());
			DataInputStream dis = new DataInputStream(server.getInputStream());
			
			dos.writeLong(time);
			dos.writeDouble(x);
			dos.writeDouble(y);
			dos.writeDouble(z);
			dos.flush();			//se envia la movida

			returnData = dis.readBoolean();
			
			
			dos.close();
			dis.close();
			server.close();

			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
			return returnData;
		}
		
	}

	/**
	 * Retorna todas las muestras almacenadas en el buffer
	 * @return las muestras almacenadas en el buffer
	 */
	public synchronized Position[] getPosition(){
		Position[] rPos = new Position[position.size()];
		int i = 0;
		while(!position.isEmpty()){
			rPos[i++] = position.removeFirst();
		}
		return rPos;
	}
}
