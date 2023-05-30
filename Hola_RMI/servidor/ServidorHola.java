/**
 * ServidorHola.java
 *
 * Ejemplo sencillo de servidor RMI
 *
 * @author RCSD
 * @version 2020
 */

package servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import common.Hola;

public class ServidorHola implements Hola {

    public ServidorHola() {}

    public String di_hola() {
        final String elMensaje = new String ("Hola nuevo Mundo");
        return elMensaje;
    }

    public static void main(String args[]) {        
        try {	
            ServidorHola servidor = new ServidorHola();
            Hola elServidor = (Hola) UnicastRemoteObject.exportObject(servidor, 0);

            // Creamos un servidor de nombres que atiende por el puerto por defecto (1099)
            Registry registry = LocateRegistry.createRegistry(1099);	   
            // Registra el stub del objeto remoto en el servidor de nombres
            registry.rebind("Servidor_Hola", elServidor);

            System.out.println("Servidor_Hola instalado");
        } catch (Exception e) {
            System.err.println("Excepcion del servidor: " + e.getMessage());
        }
    }
}

