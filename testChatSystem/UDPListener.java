import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class UDPListener implements Runnable {

	private ListenerThread listenerThread = null;
	private ClientThread clientThread = null; 

	public UDPListener(ListenerThread listenerThread, ClientThread clientThread) {
		this.listenerThread = listenerThread;
		this.clientThread = clientThread;
	}

	public void run() {
		try {
			// Creating datagram socket
			DatagramSocket dgramSocket = new DatagramSocket(4000);
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
			while(true) {
				String number = null;
				// Datagram reception
				dgramSocket.receive(inPacket);
				String data = new String(inPacket.getData(), 0, inPacket.getLength());
				InetAddress clientAddr = inPacket.getAddress();
				// If host receive his own broadcast packet, nothing to do
				if ( isItOwnIP(clientAddr) == false ) {
					// If message is "alert", send number of user and own pseudo
					if (data.equals("alert")) {
						String response = Integer.toString( listenerThread.getClientList().size() ) + "," + clientThread.getMainUserName();
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						dgramSocket.send(outPacket);
					}

					// If the message is number and pseudo, register user until number = 0
					else if ( data.indexOf(",") != (-1) ) {
						int separator = data.indexOf(",");
						String pseudo = null;
						// Only for the first message
						if (number == null) {
						// Finding number of users
							for (int i = 0 ; i < separator ; i++) {
								number += data.charAt(i);
							}
						}
						// Finding pseudo
						for (int i = (separator + 1) ; i <= data.length() ; i++ ) {
							pseudo += data.charAt(i);
						}
						// Add the user to the list
						sendUser(pseudo, clientAddr);
					}

					// Else message is a pseudo, if it's a not registered pseudo, add it to the users list
					else if (isUserExist(data) == false) {
							sendUser(data, clientAddr);
							// Answering the new connection alert
							String response = clientThread.getMainUserName();
							DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
							dgramSocket.send(outPacket);
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}  	

	}

	public void sendUser(String name, InetAddress addr) {
		listenerThread.addNewConnectedUser(name, addr);
	}

	public boolean isUserExist(String name) {
		return listenerThread.isUserExist(name);
	}

	public boolean isItOwnIP(InetAddress clientAddr) {
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			
			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					// clientAddr is formated like "/X.X.X.X"
					if ( clientAddr.toString().equals("/" + i.getHostAddress()) ) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
