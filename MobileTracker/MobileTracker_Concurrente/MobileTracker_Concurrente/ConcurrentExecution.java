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
package mobileTrackerConcurrente;
public class ConcurrentExecution {

	public static void main(String[] args) {
		// Crea el buffer
		Buffer buff=new Buffer();
		// Crea el movil
		new Mobile(buff);
		// Crea el follower
		new Follower(buff);
	}
}
