package logPartition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;

import common.ILogService;

public class ProxyServer extends Thread{

	private ILogService lSer;
	private int SOCKET = 20010;
	private InetAddress SERVER_ADDR;

	private DatagramSocket server = null;
	private DatagramPacket packet;
	private byte[] data;


	public ProxyServer(ILogService logService){
		this.lSer = logService;
		try{
			SERVER_ADDR = InetAddress.getByName("127.0.0.1");
			//this.setDaemon(true);
			start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void run(){
		while(!Thread.interrupted()) {
			try{

				//clientSocket = serverSocket.accept();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				server = new DatagramSocket();

				DataOutputStream dos = new DataOutputStream(baos);


				data=new byte[500];
				packet=new DatagramPacket(data,500);
				server.receive(packet);

				DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));


				int id= dis.readInt();

				switch(id){

				case 0:
					//lee los parametros del logData
					String winner = dis.readUTF();
					String oponent = dis.readUTF();
					long score = dis.readLong();

					//llama a logData
					boolean res = lSer.logData(winner, oponent, score);
					//escribe el booleano y lo envia
					dos.writeBoolean(res);
					dos.flush();


					data=baos.toByteArray();
					packet=new DatagramPacket(data, data.length,SERVER_ADDR, SOCKET);
					server.send(packet);

					break;

				case 1:

					long size = lSer.getLogSize();
					dos.writeLong(size);
					dos.flush();
					data=baos.toByteArray();
					packet=new DatagramPacket(data, data.length,InetAddress.getLocalHost(), SOCKET);
					server.send(packet);
					break;


				}
				dos.close();
				dis.close();


			}catch(IOException e){
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

}
