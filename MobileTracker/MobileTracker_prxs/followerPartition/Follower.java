/**********************************************************************************
 *   ********** Computer and Real-Time Group . University of Cantabria **********
 *                       *** MobileTracker Projects ***   
 *   
 *   Clase activa: Follower
 *   M�dulo activo que muestra off-line la trayectoria del movil con un tiempo de
 *   representaci�n de 2 s. Opera de forma espor�dica con tiempos de activaci�n  
 *   comprendidos entre 1 y 8 s,
 *   
 *    Author: J.M. Drake
 *    Version: 04-05-2014
 *
 **********************************************************************************/
public class Follower extends Thread {
	private Buffer buff;   		// Referencia el buffer del que obtiene los datos
	private long lastUpdate=0;  // Tiempo en el que se represento el ultimo dato
	private static final int T_DISPLAY = 2000;

	// Constructor
	public Follower(Buffer buff) {
		this.buff=buff;
		start();
	}

	/**
	 * Procesa cada muestra que se lee. Cada 2s muestra un punto de la trayectoria 
	 * @param pos muestra a procesar
	 */
	public void processPosition(Position pos){
		if (pos.time-lastUpdate>=T_DISPLAY){
			System.out.println("Time: "+formated(pos.time/1000.0)+
					" s  x= "+formated(pos.x)+
					" m  y= "+formated(pos.y)+
					" m  z= "+formated(pos.z)+" m");
			lastUpdate=pos.time;
		}
	}

	// Actividad interna del Follower
	public void run(){
		while(true){ 	// Emula un comportamiento esporadico con retrasos incluidos entre 1s y 8 s
			try{
				Thread.sleep(1000+(long)(7000*Math.random()));
			}catch (InterruptedException e){}
			// Obtiene los datos del buffer
			Position[] pos=buff.getPosition();
			// Si no hay datos, supone que el movil ha finalizado.
			if ((pos.length==0)&&(lastUpdate>0)) break;
			// Procesa cada uno de los datos recibidos.
			for(Position p:pos){
				processPosition(p);
			}
		}
	}

	/**
	 * Funcion auxiliar para formatear los datos que se muestran en la consola
	 * @param d numero a formatear
	 * @return dato formateado a string
	 */
	private static String formated(double d){
		String r=Double.toString(d);
		while(r.length()<7)r=r+"0";
		return r.substring(0, 6);
	}

}
