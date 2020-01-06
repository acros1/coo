
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
            udpListener = new UDPListener(listenerThread, this);
            listenerThread = new mainSystem(this);
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
			this.broadcastPseudo(0);//firstly we get the other users pseudo !
			
			// Asking user which action realise
			

		} catch (IOException e) {
			e.printStackTrace();
		}            
   	}

	public void broadcastPseudo(int code) {
		try {
                //the code is used to know what kind of broadcast it is, in fact the code : 0 means that the user just log in and want to get all the users' pseudo in order to choose his
                String sendMesg = null;    
                if(code == 0){
                    sendMesg = "!0!";
                    this.outPacket = new DatagramPacket(sendMesg.getBytes(), sendMesg.length(), InetAddress.getByName("255.255.255.255"), 4000);
                    this.dgramSocket.send(this.outPacket);
                    }
                else{
                    sendMesg = mainUser.getPseudo();
                    this.outPacket = new DatagramPacket(sendMesg.getBytes(), sendMesg.length(), InetAddress.getByName("255.255.255.255"), 4000);
                    this.dgramSocket.send(this.outPacket);  
                }
                
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
            this.broadcastPseudo(1);
        }
        
        public mainSystem getMainSystem(){
            return this.listenerThread;
        }
        
        public void setApplicationWindow(applicationWindow ApplicationWindow){
            this.aW = ApplicationWindow;
        }
        
        public applicationWindow getApplicationWindow(){
            return this.aW;
        }

	public void changePseudo(String newPseudo) {
		//try {
			String oldPseudo = "#" + this.mainUser.getPseudo();
			// Asking for pseudo
			System.out.println("Your new pseudo :");
			this.mainUser.setPseudo(newPseudo);
			// Broadcasting pseudo
			this.broadcastPseudo(1);
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