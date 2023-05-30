package playPartition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProxyClient {

    private static final String FOLLOWER_IP = "127.0.0.1";
    private static final int PORT = 6697;
    private InetAddress SERVER_ADDRESS;

    public ProxyClient(){
        try{
            SERVER_ADDRESS=InetAddress.getByName(FOLLOWER_IP);
        } catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * 
     */
    public boolean logData(String winner, String opponent, long score){
        
        boolean res = false;

        try{
            Socket server = new Socket(InetAddress.getLocalHost(),PORT);

            DataOutputStream dos = new DataOutputStream(server.getOutputStream());

            dos.writeInt(0);

            dos.writeUTF(winner);
			dos.writeUTF(opponent);
			dos.writeLong(score);

            dos.flush();
            

            DataInputStream dis = new DataInputStream(server.getInputStream());
            res= dis.readBoolean();

            server.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);

        }
        return res;

    }


    public long getLogSize(){
        long tam=0;
        try {
			Socket socket = new Socket(InetAddress.getLocalHost(),PORT);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			dos.writeInt(1);//si es 1 significa que es la funcion showStatsInfo
			
			tam = dis.readLong();//llamada bloqueante
			System.err.println("(DEBUG) Log size: "+tam+" bytes");
			
			//liberamos recursos
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
        return tam;
    }
}
