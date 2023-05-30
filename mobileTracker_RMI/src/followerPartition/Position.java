/**********************************************************************************
 *   ********** Computer and Real-Time Group . University of Cantabria **********
 *                       *** MobileTracker Projects ***   
 *   
 *   Tipo de dato: position
 *   Tipo de datos que corresponde a cada lectura de la posici�n del m�vil.
 *      
 *    Author: J.M. Drake
 *    Version: 04-05-2014
 *
 **********************************************************************************/
package mobileTrackerConcurrente;

public class Position {
	public long time;
	public double x;
	public double y;
	public double z;
	// Constructor
	public Position(long t,double x,double y,double z){
		this.time=t;
		this.x=x;
		this.y=y;
		this.z=z;
	}
}
