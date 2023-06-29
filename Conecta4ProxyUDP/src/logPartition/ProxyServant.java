package logPartition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import common.ILogService;

public class ProxyServant extends Thread{
	private static final int PUERTO = 20010;
	
	private ILogService logger;
	private byte[] data;
	private byte[] dataReturn;
	
	public ProxyServant(ILogService logger) {
		this.logger = logger;
		this.run();
	}
	
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(PUERTO, InetAddress.getLocalHost());
			
			while(!interrupted()) {
				dataReturn = new byte[256];
				DatagramPacket paquete = new DatagramPacket(dataReturn, dataReturn.length);
				serverSocket.receive(paquete);
				
				DataInputStream dis = new DataInputStream(new ByteArrayInputStream(paquete.getData()));
				int id = dis.readInt();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos);
				
				switch(id) {
				case 1:
					String winner = dis.readUTF();
					String opponent = dis.readUTF();
					long score = dis.readLong();
					
					boolean retorno = logger.logData(winner, opponent, score);
					
					dos.writeBoolean(retorno);
					dos.flush();
					
					data = baos.toByteArray();
					paquete.setData(data);
					paquete.setLength(data.length);
					
					serverSocket.send(paquete);
					break;
				case 2:
					long longitudDocumento = logger.getLogSize();
					dos.writeLong(longitudDocumento);
					
					data = baos.toByteArray();
					
					paquete.setData(data);
					paquete.setLength(data.length);
					
					serverSocket.send(paquete);
					break;
				}
				
			}
			
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
