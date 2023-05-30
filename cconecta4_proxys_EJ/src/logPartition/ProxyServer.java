package logPartition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

import common.ILogService;

public class ProxyServer extends Thread{

    private ILogService lSer;
    private ServerSocket serverSocket;

    public ProxyServer(ILogService logService){
        this.lSer = logService;
        try{
            serverSocket = new ServerSocket(6697);
            //this.setDaemon(true);
            start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            clientSocket serverSocket.accept();

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            int id = dis.readInt();
            switch(id){
                case 0:
                    String winner = dis.readUTF();
                    String opponent = dis.readUTF();
                    long score = dis.readLong();

                    boolean res = lSer.logData(winner, oponent, score);
                    dos.writeBoolean(res);
                    break;

                case 1:
                    long size = lSer.getLogSize();
                    dos.writeLong(size);
                    break;
                
            
            }
            clientSocket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        serverSocket.close();
    }

}
