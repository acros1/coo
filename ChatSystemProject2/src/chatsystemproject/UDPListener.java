package chatsystemproject;


import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.Enumeration;

import java.net.NetworkInterface;
import javax.swing.JOptionPane;

public class UDPListener implements Runnable {

	private mainSystem listenerThread = null;
	private ClientThread clientThread = null; 
        private boolean pseudoGood = false;

	

	public UDPListener(mainSystem listenerThread, ClientThread clientThread) {
		this.listenerThread = listenerThread;
		this.clientThread = clientThread;
	}

	public void run() {
		try {
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
				if ( isItOwnIP(clientAddr) == false ) {

					// If first char is "#" pseudo has to be deleted from the list (disconection or pseudo already used)
					if ( Character.toString( data.charAt(0) ).compareTo("#") == 0 ) {
						System.out.println("Received a pseudo to delete");
						String pseudoToDelete = data.substring(1);
						listenerThread.deleteUser(pseudoToDelete);
					}

					// If first char is "|", then message is an answer of pseudo broadcast
					// Answer is format like "|mainUserPseudo|boolean|clientPseudo"
					else if ( Character.toString( data.charAt(0) ).compareTo("|") == 0 ) {
						System.out.println("Received an answer to pseudo broadcast");
						String[] dataSplit = data.split("\\|"); // String array, each element is text between "|"
						// dataSplit[0] is empty, dataSplit[1] = mainUserPseudo, dataSplit[2] = boolean, dataSplit[3] = clientPseudo
						System.out.println("0 : \""+dataSplit[0]+"\" 1 : \""+dataSplit[1]+"\" 2 : \""+dataSplit[2]+"\" 3 : \""+dataSplit[3]+"\"");
						// if main user pseudo is not equal to dataSplit[1], pseudo has already been changed, then don't process message
						if ( dataSplit[1].equals(clientThread.getMainUserPseudo()) ) {

							// if boolean = false, client already has main user pseudo, then ask for a new pseudo
							if ( dataSplit[2].equals("false") ) {

								/*clientThread.changePseudoState(false);
								System.out.println("isPseudoOk = " + clientThread.getIsPseudoOk());*/
								System.out.println("Pseudo is already used by another client");
								System.out.println("Sending alert to others users, they have to delete my pseudo from their list");
								String response = "#" + clientThread.getMainUserPseudo();
								DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
								dgramSocket.send(outPacket);
								// Asking for new pseudo
								String newPseudo = JOptionPane.showInputDialog("Please enter your new pseudo");
								//System.out.println("Your new pseudo :");
								//String newPseudo = scan.nextLine();
								clientThread.changePseudo(newPseudo);

								// if clientUser is not in the list yet, add him
								if ( listenerThread.isUserExist(dataSplit[3]) == false ) {
									System.out.println("Client is not in the list, adding him");
									listenerThread.addUser(dataSplit[3], clientAddr);
								}

							}

							// boolean = true, then pseudo is not used by this client so just add client to the users list
							else {
								/*clientThread.changePseudoState(true);
								System.out.println("isPseudoOk = " + clientThread.getIsPseudoOk());*/
								// if clientUser is not in the list yet, add him
                                                                this.pseudoGood = true;
								if ( listenerThread.isUserExist(dataSplit[3]) == false ) {
									System.out.println("Client is not in the list, adding him");
									listenerThread.addUser(dataSplit[3], clientAddr);
								}
							}

						}

					}
					// Else it's a pseudo broadcast, then check if pseudo is not equal as main user pseudo
					else {
						// if pseudo is the same as main user pseudo
						if ( data.equals(clientThread.getMainUserPseudo()) ) {
							// Answering with false boolean
							System.out.println("Pseudo is already mine, sending false");
							String response = "|" + data + "|" + "false" + "|" + clientThread.getMainUserPseudo();
							DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
							dgramSocket.send(outPacket);
						}
						// else pseudo is not the same
						else {
							// Answering with true boolean
							System.out.println("Pseudo is different of mine, sending true");
							String response = "|" + data + "|" + "true" + "|" + clientThread.getMainUserPseudo();
							DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddr, 4000);
							dgramSocket.send(outPacket);
							// Add user to the list
							if ( listenerThread.isUserExist(data) == false ) {
								System.out.println("Client is not in the list, adding him");
								listenerThread.addUser(data, clientAddr);
							}
						}
					}
				}
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
        
        public boolean isPseudoGood(){
            return pseudoGood;
        }

}
