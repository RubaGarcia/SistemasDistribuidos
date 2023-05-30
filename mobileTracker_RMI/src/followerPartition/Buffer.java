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
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class Buffer extends UnicastRemoteObject implements BufferRemote{
	private final int BUFFER_SIZE = 20; // Tamanho maximo del buffer
	private LinkedList<Position> position = new LinkedList<Position>();

	protected Buffer()throws RemoteException{
		super();
	}


	private static final long serialVersionUID = 1L;


	/**
	 * Introduce en el buffer una posición
	 * @params datos de la posición
	 * @return false si el buffer esta lleno, true si la posición es almacenada
	 */
	public synchronized boolean putPosition(long time,double x,double y,double z){
		boolean success = false;
		if (position.size()<BUFFER_SIZE){
			position.add(new Position(time,x,y,z));
			success = true;
		}
		return success;
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

	public void freeResources(){
		try{
			UnicastRemoteObject.unexportObject(this,true);
		}catch(NoSuchObjectException e){}
	}
}
