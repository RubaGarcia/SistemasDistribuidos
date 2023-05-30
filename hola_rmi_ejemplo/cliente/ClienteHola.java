/**
 * ClienteHola.java
 *
 * Ejemplo sencillo de cliente RMI
 *
 * @author RCSD
 * @version 2020
 */

package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import common.Hola;

public class ClienteHola {

    public static void main(String[] args) {

        String registryHost = null;
        final String nombreServidor = "Servidor_Hola";
        
        if (args.length >= 1) 
            registryHost = args[0];
        
        try {
            // Recuperamos la informacion de contacto del servidor a traves del servidor de nombres
            // Si registryHost es null se utiliza el localhost
            Registry registry = LocateRegistry.getRegistry(registryHost);
            Hola elServidor = (Hola) registry.lookup(nombreServidor);
            
            // Invocamos el servicio remoto
            String respuesta = elServidor.di_hola();
            System.out.println ("Respuesta del servidor:  " + respuesta);
        } catch (Exception e) {
            System.err.println ("Excepcion del cliente: " + e.getMessage());
        }
    }
}

