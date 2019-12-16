/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
import java.io.*;
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
			System.out.println("Your name :");
			String pseudo = scan.nextLine();
			mainUser = new User(pseudo, null);
		
			// Sending new connection alert
			DatagramSocket dgramSocket = new DatagramSocket();

			String alert = pseudo;
			DatagramPacket outPacket = new DatagramPacket(alert.getBytes(), 
											alert.length(), 
											InetAddress.getByName("255.255.255.255"), 4000);
			dgramSocket.send(outPacket);

			while(true) {
				System.out.println("Command (list or send) :");
				input = scan.nextLine();
				command(input);
			}
		} catch (Exception e) {
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

	public String getMainUserName() {
		return this.mainUser.getPseudo();
	}

	public static void main (String[] args) {
		new Thread(new ClientThread()).start();
	}              
} 

