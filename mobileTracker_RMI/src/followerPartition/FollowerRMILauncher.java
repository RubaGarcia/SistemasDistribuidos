package followerPartition;

import java.rmi.registry.LocateRegistry;

public class FollowerRMILauncher {
    public static void main(String[] args){
        Buffer buffer = null;
        Follower follower = null;

        try{
            buffer = new Buffer();
            follower = new Follower(buffer);
        }catch(RemoteException re1){
            re1.printStackTrace();
            System.exit(-1);
        }
        Registry registry = null;
        try{
            registry = LocateRegistry.createRegistry(1099);

        }catch(RemoteException re2){
            re2.printStackTrace();
            System.exit(-1);
        }
        try {
            registry.bind("Jarama", buffer);
        } catch (Exception e) {
            System.out.println("movidas");
        }
    }
}
