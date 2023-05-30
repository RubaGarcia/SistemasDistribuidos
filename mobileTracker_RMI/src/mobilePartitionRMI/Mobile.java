
/**********************************************************************************
 *   ********** Computer and Real-Time Group . University of Cantabria **********
 *                       *** MobileTracker Projects ***   
 *   
 *   Clase activa: Mobile
 *   Muestrea periodicamente (cada 250 ms) la possici�n del movil y los registra en
 *   el buffer.
 *   
 *    Author: J.M. Drake
 *    Version: 28-05-2013
 *
 **********************************************************************************/
package mobileTrackerConcurrente;

import java.rmi.RemoteException;
import java.util.LinkedList;

public class Mobile extends Thread{
	private final long TIEMPO_EJECUCION=50000; 	// Transcurrido este tiempo el movil termina.
	BufferRemote buffer;				// Referencia al buffer

	// Constructor. Arranca la actividad del movil.	
	public Mobile(Buffer buff){
		this.buffer=buff;
		start();
	}

	// Actividad del movil
	public void run(){
		long TSAMPLE=250;    // Periodo de muestreo de la posicion en ms
		double Rx= 1000.0;   // Datos que definen la emulacion de la
		double Ry=500.0;     // trayectoria del movil
		double Rz=100;       // --
		double Xo= 0.0;      // --
		double Yo=0.0;       // --
		double Zo=3*Rz;      // --
		double Tx=20000.0;   // --
		double Ty=100000.0;  // --
		double Tz=100000.0;  // --
		long time;
		double x=0;
		double y=0;
		double z=0;
		// Tiempo en el que comienza el movimiento. Origen del tiempo.
		long inicTime=System.currentTimeMillis();
		// Tiempo en el que tomara la proxima posicion
		long nextTime=inicTime+TSAMPLE;
		// Lista de posiciones pendientes de ser enviadas
		LinkedList<PositionMobile> pendingPos=new LinkedList<PositionMobile>();
		// Opera periodicamente hasta que es interrupmido externamente turnOff()
		while(!this.isInterrupted()){
			time=nextTime-inicTime;
			x=Xo+Rx*Math.sin(time/Tx*2*Math.PI);
			y=Yo+Ry*Math.sin(time/Ty*2*Math.PI);
			z=Zo+Rz*Math.sin(time/Tz*2*Math.PI);
			pendingPos.add(this.new PositionMobile(time,x,y,z));



			try{
				if(buffer.putPosition(pos.time,pos.x,pos.y,pos.z)){
					pendingPos.removeFirst();
				}else{
					break;
				}
			} catch(RemoteException e){
				e.printStackTrace();
				//System.exit(-1);
				turnOff(); //porque???
			}

			




			if(nextTime-System.currentTimeMillis()>0){
				try{sleep(nextTime-System.currentTimeMillis());
				}catch(InterruptedException e){break;}
			}
			if(nextTime-inicTime>TIEMPO_EJECUCION) {
			    this.turnOff();
			}
			nextTime+=TSAMPLE; 
		}
	}

	// Finaliza la actividad del movil.
	public void turnOff(){
		interrupt();
	}

	private class PositionMobile {
		public long time;
		public double x;
		public double y;
		public double z;
		// Constructor
		public PositionMobile(long t,double x,double y,double z){
			this.time=t;
			this.x=x;
			this.y=y;
			this.z=z;
		}
	}
}
