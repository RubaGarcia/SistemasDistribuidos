package playPartition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ProxyClient {

    private static final String FOLLOWER_IP = "127.0.0.1";
    private static final int PORT = 20010;
    private InetAddress SERVER_ADDRESS;

    
    private DatagramSocket serv = null;
	private DatagramPacket packet;
	private byte[] data;
	
	
    public ProxyClient(){
        try{
            SERVER_ADDRESS=InetAddress.getByName(FOLLOWER_IP);
        } catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    
    public boolean logData(String winner, String opponent, long score){
        
        boolean res = false;

        try{
        	
        	
        	serv=new DatagramSocket();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            DataOutputStream dos = new DataOutputStream(baos);
            
            data = new byte[winner.length()+opponent.length()+Long.BYTES + Integer.BYTES];
            packet = new DatagramPacket(data,data.length);
            
            dos.writeInt(0);
            //System.out.println("funcion logData");
            dos.writeUTF(winner);
			dos.writeUTF(opponent);
			dos.writeLong(score);

            dos.flush();
            data=baos.toByteArray();
            packet=new DatagramPacket(data, data.length,SERVER_ADDRESS,PORT);
            serv.send(packet);
            
            
            data=new byte[1];
            packet = new DatagramPacket(data, 1);
            serv.receive(packet);
            
            
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
            res= dis.readBoolean();

            serv.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);

        }
        return res;

    }


    public long getLogSize(){
    	//System.out.println("funcion logSize");
        long tam=0;
        try {
			//Socket socket = new Socket(InetAddress.getLocalHost(),PORT);
			serv = new DatagramSocket();
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
        	DataOutputStream dos = new DataOutputStream(baos);
			
			
			dos.writeInt(1);//si es 1 significa que es la funcion showStatsInfo
			dos.flush();
			
			data=baos.toByteArray();
			
			data = new byte[Long.BYTES];
			packet = new DatagramPacket(data, data.length,SERVER_ADDRESS, PORT);
			serv.send(packet);
			
			data = new byte[Long.BYTES];
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
			tam = dis.readLong();//llamada bloqueante
			System.err.println("(DEBUG) Log size: "+tam+" bytes");
			
			//liberamos recursos
			
			serv.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
        return tam;
    }
}
