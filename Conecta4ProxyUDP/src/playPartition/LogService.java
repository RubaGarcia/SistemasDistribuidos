package playPartition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import common.ILogService;

public class LogService implements ILogService{
	
	private static final int PUERTO = 20010;
	
	private byte[] data;
	private byte[] dataReturn = new byte[Long.BYTES];
	
	public boolean logData(String winner, String opponent, long score) {
		int idNum = 1;
		boolean retorno = false;
		
		try {
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket paquete;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			dos.writeInt(idNum);
			dos.writeUTF(winner);
			dos.writeUTF(opponent);
			dos.writeLong(score);
			dos.flush();
			
			data = baos.toByteArray();
			
			paquete = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), PUERTO);
			socket.send(paquete);
			
			paquete = new DatagramPacket(dataReturn, 1);
			socket.receive(paquete);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(dataReturn));
			retorno = dis.readBoolean();
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return retorno;
	}
	
	public long getLogSize() {
		int idNum = 2;
		long retorno = -1;
		
		try {
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket paquete;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			dos.writeInt(idNum);
			
			data = baos.toByteArray();
			
			paquete = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), PUERTO);
			socket.send(paquete);
			
			paquete = new DatagramPacket(dataReturn, Long.BYTES);
			socket.receive(paquete);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(dataReturn));
			retorno = dis.readLong();
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return retorno;
	}
}
