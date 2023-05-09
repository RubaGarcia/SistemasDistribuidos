import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Buffer_server extends Thread {
    private static final int PORT = 12000;
    private Buffer buffer;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Buffer_server(Buffer buffer){
        this.buffer=buffer;
        try{
            serverSocket=new ServerSocket(PORT);
            this.setDaemon(true);
            start();
        }catch(IOException e){
            System.out.println("Problema en la creacion del ServerSocket");
        }
    }

    public void run(){
        while(!Thread.interrupted()){
            try{
                clientSocket = serverSocket.accept();

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                long time = dis.readLong();
                double x = dis.readDouble();
                double y = dis.readDouble();
                double z = dis.readDouble();

                boolean res = buffer.putPosition(time, x, y, z);

                dos.writeBoolean(res);
                dos.flush();

                dis.close();
                dos.close();

            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Fallo en la ejecución del server");
            }

        }
        try{
            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

//TODO ver que tiene esta cosa
    public class BufferAgent implements Runnable {
        private Socket clientSocket;

        public BufferAgent(Socket clientSocket){
            this.clientSocket=clientSocket;
        }
        public void run(){
            //que coño se hace acá
        } 
        
    }
}
