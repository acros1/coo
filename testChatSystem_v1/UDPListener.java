import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.Enumeration;
import java.net.NetworkInterface;

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
				String data = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("New broadcast alert receive, new user : " + data);
				InetAddress clientAddr = inPacket.getAddress();
				// If received broadcast is coming from localhost, don't process it
				if ( isItOwnIP(clientAddr) == false ) {

					// If data = "number" answer with the number of users in the list
					if ( data.equals("number") ) {
						// Getting size of the user list = number of users connected
						String response = Integer.toString( listenerThread.getClientList().size() );
						System.out.println("Receiving \"number\", answering with : " + response);
						// Creating packet
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						// Sending packet
						dgramSocket.send(outPacket);
					}

					// If received pseudo is already is the list, don't add it in the list
					if (isUserRegistered(data) == false) {
						sendUser(data, clientAddr);
						System.out.println(data + " is not in the list yet, answering with : " + clientThread.getMainUserName());

						// Answering the new connection alert
						String response = clientThread.getMainUserName();
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						dgramSocket.send(outPacket);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  	

	}

	public void sendUser(String name, InetAddress addr) {
		listenerThread.addNewConnectedUser(name, addr);
	}

	public boolean isUserRegistered(String name) {
		return listenerThread.isUserRegistered(name);
	}

	// Function to test if clientAddr is equal to localhost
	public boolean isItOwnIP(InetAddress clientAddr) {
		try {
			// Getting every interface of the computer
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			
			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				// Getting address of every interfaces
				Enumeration ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					// clientAddr is formated like "/X.X.X.X"
					// If interface address is equal to clientAddr, return true
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
