/**********************************************************************************
 *   ********** Computer and Real-Time Group . University of Cantabria **********
 *                       *** MobileTracker Projects ***   
 *   
 *   Programa principal: ConcurrentExecution
 *   Lanza la aplicaci�n sobre una �nica JVM a modo de ejemplo de la funcionalidad.
 *   
 *    Author: J.M. Drake
 *    Version: 04-05-2014
 *
 **********************************************************************************/

public class FollowerExecution {

	public static void main(String[] args) {
		// Crea el buffer
		Buffer buff=new Buffer();
		// Crea el movil
		//new Mobile(buff);
		// Crea el follower
		Follower follower = new Follower(buff);
		new Buffer_server(buff);
	}
}
