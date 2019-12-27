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

	private boolean isPseusoOk = false;

	public ClientThread() {
		listenerThread = new ListenerThread();
		udpListener = new UDPListener(listenerThread, this);
	}

	public void run() {
		try {
			// Starting threads
			Thread tListener = new Thread(listenerThread);
			tListener.start();
			Thread tUdp = new Thread(udpListener);
			tUdp.start();
			// Asking for pseudo
			System.out.println("Your pseudo :");
			this.pseudo = scan.nextLine();

			
						
			// Creating datagram socket to send UDP messages
			this.dgramSocket = new DatagramSocket();
			
			// Broadcasting pseudo
			this.outPacket = new DatagramPacket(this.pseudo.getBytes(), this.pseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
			this.dgramSocket.send(this.outPacket);

			//mainUser = new User(pseudo, null);
		
			/*try {
				Thread.sleep(5000);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/

			while( true ) {
				while ( this.isPseusoOk ) {
					System.out.println("Command (list or send) :");
					this.input = this.scan.nextLine();
					command(this.input);
				}
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
		return this.pseudo;
	}

	public void chansePseudoState(boolean state) {
		this.isPseusoOk = state;
	}

	public void changePseudo() {
		try {
			// Asking for pseudo
			System.out.println("Your new pseudo :");
			this.pseudo = scan.nextLine();
			// Broadcasting pseudo
			this.outPacket = new DatagramPacket(pseudo.getBytes(), pseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
			this.dgramSocket.send(this.outPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) {
		new Thread(new ClientThread()).start();
	}              
} 
