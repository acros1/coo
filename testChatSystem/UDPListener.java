import java.net.*;

public class UDPListener implements Runnable {

	private ListenerThread listenerThread = null;
	private ClientThread clientThread = null; 

	public UDPListener(ListenerThread listenerThread, ClientThread clientThread) {
		this.listenerThread = listenerThread;
		this.clientThread = clientThread;
	}

	public void run() {
		try {
			DatagramSocket dgramSocket = new DatagramSocket(4000);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
			while(true) {
				// New user is connected, datagram reception
				dgramSocket.receive(inPacket);
				String clientName = new String(inPacket.getData(), 0, inPacket.getLength());
				InetAddress clientAddr = inPacket.getAddress();
				int clientPort = inPacket.getPort();
				sendUser(clientName, clientAddr);
				
				// Answering the new connection alert
				System.out.println("BEFORE");
				System.out.flush();
				String response = clientThread.getMainUserName();
				DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, clientPort);
				dgramSocket.send(outPacket);
				System.out.println("AFTER");
				System.out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  	

	}

	public void sendUser(String name, InetAddress addr) {
		listenerThread.addNewConnectedUser(name, addr);
	}	

}
