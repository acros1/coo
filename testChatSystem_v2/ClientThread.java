import java.io.IOException;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {

	private User mainUser = null;
	private String pseudo = null;

	private ListenerThread listenerThread = null;
	private UDPListener udpListener = null;

	private DatagramPacket outPacket = null;
	private DatagramSocket dgramSocket = null;

	private Scanner scan = new Scanner(System.in);
	private String input = null;

	//private boolean isPseudoOk = false;

	public ClientThread() {
		listenerThread = new ListenerThread();
		udpListener = new UDPListener(listenerThread, this);
	}

	public void run() {
		try {			
			// Asking for pseudo
			System.out.println("Your pseudo :");
			this.pseudo = scan.nextLine();

			// Starting threads
			Thread tListener = new Thread(listenerThread);
			tListener.start();
			Thread tUdp = new Thread(udpListener);
			tUdp.start();	

			// Initializing datagram socket to send UDP messages
			this.dgramSocket = new DatagramSocket();
			
			// Broadcasting pseudo
			this.broadcastPseudo();
			
			// Asking user which action realise
			this.command();

		} catch (IOException e) {
			e.printStackTrace();
		}            
   	}

	public void broadcastPseudo() {
		try {
		this.outPacket = new DatagramPacket(this.pseudo.getBytes(), this.pseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
		this.dgramSocket.send(this.outPacket);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void command() {
		String user = null;

		while ( true ) {
			System.out.println("Command (list or send) :");
			this.input = this.scan.nextLine();

			// Display users list
			if(this.input.equals("list")) {
				for(User u : listenerThread.getClientList()) {
					System.out.println(u.getPseudo());
				}
			}

			else if(this.input.equals("send")) {
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
					user = this.scan.nextLine();
					for(User u : listenerThread.getClientList()) {
						if(u.getPseudo().equals(user)) {
							ServerThread st = listenerThread.getServer(u);
							System.out.println("Type your message :");
							user = this.scan.nextLine(); // ask for msg to send
							st.writeMessage(user);
						}
						else {
							System.out.println("User not find");
						}
					}
				}
			}
		}	
	}

	public String getMainUserPseudo() {
		return this.pseudo;
	}
	/*
	public void changePseudoState(boolean state) {
		this.isPseudoOk = state;
	}

	public boolean getIsPseudoOk() {
		return this.isPseudoOk;
	}
	*/

	public void changePseudo(String newPseudo) {
		//try {
			String oldPseudo = "#" + this.pseudo;
			this.pseudo = newPseudo;
			// Broadcasting pseudo
			this.broadcastPseudo();
			// Broadcast message to delete old pseudo in other users list
			try {
				this.outPacket = new DatagramPacket(oldPseudo.getBytes(), oldPseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
				this.dgramSocket.send(this.outPacket);
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			// Asking user which action realise
			//this.command();
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}

	public static void main (String[] args) {
		new Thread(new ClientThread()).start();
	}              
} 
