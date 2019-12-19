import java.io.IOException;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {

	private User mainUser = null;

	private ListenerThread listenerThread = null;
	private UDPListener udpListener = null;

	public ClientThread() {
		listenerThread = new ListenerThread();
		udpListener = new UDPListener(listenerThread, this);
	}

	public void run() {
		try {
			Thread tListener = new Thread(listenerThread);
			tListener.start();
			Thread tUdp = new Thread(udpListener);
			tUdp.start();
			Scanner scan = new Scanner(System.in);
			String input = null;
			String data = null;
			
			// Creating datagram socket to send UDP messages
			DatagramSocket dgramSocket = new DatagramSocket();

			// Sending broadcast message with data "number" to get the number of users connected
			System.out.println("Sending broadcast \"number\"");
			data = "number";
			DatagramPacket outPacket = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("255.255.255.255"), 4000);

			System.out.println("Your pseudo :");
			String pseudo = scan.nextLine();
			mainUser = new User(pseudo, null);
		
			

			data = pseudo;
			outPacket = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("255.255.255.255"), 4000);
			dgramSocket.send(outPacket);

			while(true) {
				System.out.println("Command (list or send) :");
				input = scan.nextLine();
				command(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}            
   	}

	public void command(String input) {
		Scanner scan = new Scanner(System.in);
		String user = null;

		// Display users list
		if(input.equals("list")) {
			for(User u : listenerThread.getClientList()) {
				System.out.println(u.getPseudo());
			}
		}

		else if(input.equals("send")) {
			// Printing users list
			if (listenerThread.getClientList().size() == 0) {
				System.out.println("<No user connected>");
			}
			else {
				// Asking for user to send msg
				System.out.print("Which user : ");
				for(User u : listenerThread.getClientList()) {
					System.out.print(u.getPseudo() + " | ");
				}
				System.out.println();
				user = scan.nextLine();
				for(User u : listenerThread.getClientList()) {
					if(u.getPseudo().equals(user)) {
						ServerThread st = listenerThread.getServer(u);
						System.out.println("Type your message :");
						user = scan.nextLine(); // ask for msg to send
						st.writeMessage(user);
					}
					else {
						System.out.println("User not find");
					}
				}
			}
		}
	}

	public String getMainUserPseudo() {
		return this.mainUser.getPseudo();
	}

	public static void main (String[] args) {
		new Thread(new ClientThread()).start();
	}              
} 
