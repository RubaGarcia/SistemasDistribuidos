package mobilePartitionRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import mobileTrackerConcurrente.BufferRemote;

public class MobileRMILauncher {
    public static void main(String[] args){

        String FollowerName = args[0];
        String RegistryIP=args[1];
        Registry registry = null;
        BufferRemote buffer = null;

        try{
            registry = LocateRegistry.getRegistry(RegistryIP);

        } catch(RemoteException re){
            re.printStackTrace();
            System.exit(-1);
        }

        try{
            buffer = (BufferRemote)registry.lookup(FollowerName);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        new Mobile(buffer);
    }
}
