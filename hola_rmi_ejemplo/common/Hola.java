/**
 * Hola.java
 *
 * Ejemplo sencillo de interfaz remota RMI
 *
 * @author RCSD
 * @version 2020
 */

package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hola extends Remote {
    String di_hola() throws RemoteException;
}
