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
			// Counter to process only one time the message with number of users connected
			int oneTimeCounter = 0;

			// Creating datagram socket to send UDP messages
			DatagramSocket dgramSocket = new DatagramSocket(4000);
			// Creating buffer and packet to receive UDP messages
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

			while(true) {
				// Datagram reception
				dgramSocket.receive(inPacket);
				String data = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("New broadcast received, data : " + data);
				InetAddress clientAddr = inPacket.getAddress();
				// If received broadcast is coming from localhost, don't process it
				//if ( isItOwnIP(clientAddr) == false ) {

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
					// Else if data is an integer, then ask for every connected users' pseudo
					else if ( (isDataInteger(data) == true) && (oneTimeCounter <= 1) ) {
						oneTimeCounter++;
						int nUser = Integer.parseInt(data);
						String response = "getPseudo";
						System.out.println("Receiving number of connected users, answering with : " + response);
						// Creating packet
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						// Sending packet
						dgramSocket.send(outPacket);

						// Receiving every pseudo of connected user
						while ( nUser > 0 ) {
							// Receive message
							dgramSocket.receive(inPacket);
							data = new String(inPacket.getData(), 0, inPacket.getLength());
							System.out.println("New pseudo received, data : " + data);
							clientAddr = inPacket.getAddress();
							// If user is not in the list yet
							if (listenerThread.isUserExist(data) == false) {
								// Add user to the list
								listenerThread.addUser(data, clientAddr);
							}
							nUser--;
						}
					}
					// Else if data = "getPseudo", answer with main user pseudo
					else if ( data.equals("getPseudo") ) {
						String response = clientThread.getMainUserPseudo();
						System.out.println("Receiving \"getPseudo\", answering with : " + response);
						// Creating packet
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						// Sending packet
						dgramSocket.send(outPacket);
					}
					// Else data is user pseudo
					else {
						// If user is not in the list yet
						if ( listenerThread.isUserExist(data) == false ) {
							// Add it to the list
							listenerThread.addUser(data, clientAddr);
							System.out.println(data + " is not in the list yet, adding him");
						}
					}

					/* If received pseudo is already is the list, don't add it in the list
					if (isUserRegistered(data) == false) {
						listenerThread.addUser(data, clientAddr);
						System.out.println(data + " is not in the list yet, answering with : " + clientThread.getMainUserPseudo());

						// Answering the new connection alert
						String response = clientThread.getMainUserPseudo();
						DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
						dgramSocket.send(outPacket);
					}*/

				//}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  	

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

	// Function to test if data is an Integer
	public boolean isDataInteger(String data) {
		try {
			Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
