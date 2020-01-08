
package chatsystemproject;

import Interface.applicationWindow;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {

	private User mainUser = null;
	private String pseudo = null;

	private mainSystem listenerThread = null;
	private UDPListener udpListener = null;

	private DatagramPacket outPacket = null;
	private DatagramSocket dgramSocket = null;

	private Scanner scan = new Scanner(System.in);
	private String input = null;
        
        private applicationWindow aW = null;
        

	public ClientThread() {
            this.mainUser = new User(null,null);
            
            this.listenerThread = new mainSystem(this);
            this.udpListener = new UDPListener(listenerThread, this);
	}

	public void run() {
		try {			
                        

			// Starting threads
			Thread tListener = new Thread(listenerThread);
			tListener.start();
			Thread tUdp = new Thread(udpListener);
			tUdp.start();	

			// Initializing datagram socket to send UDP messages
			this.dgramSocket = new DatagramSocket();
			
			// Broadcasting pseudo
			//this.broadcastPseudo();//firstly we get the other users pseudo !
			
			// Asking user which action realise
			

		} catch (IOException e) {
			e.printStackTrace();
		}            
   	}
        
       

	public void broadcastPseudo() {
		try {
                String pseudo = this.mainUser.getPseudo();
		this.outPacket = new DatagramPacket(pseudo.getBytes(), pseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
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
							ServerThread st = listenerThread.getServer(u,null);
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
		return this.mainUser.getPseudo();
	}
        
        public User getMainUser(){
            return this.mainUser;  
        }
        
        public void setMainUserPseudo(String pseudo){
            this.mainUser.setPseudo(pseudo);
        }
        
        public mainSystem getMainSystem(){
            return this.listenerThread;
        }
        
        public boolean isPseudoGood(){
            if(this.listenerThread.getClientList().isEmpty()){
                return true;
            }
            else{
                return this.udpListener.isPseudoGood();
            }
            
        }
        
        public void setApplicationWindow(applicationWindow ApplicationWindow){
            this.aW = ApplicationWindow;
        }
        
        public applicationWindow getApplicationWindow(){
            System.out.println("je retourne l'aW");
            return this.aW;
        }

	public void changePseudo(String newPseudo) {
		//try {
			String oldPseudo = "#" + this.mainUser.getPseudo();
			this.mainUser.setPseudo(newPseudo);
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
} 
