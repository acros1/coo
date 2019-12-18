/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
package chatsystemproject;

import java.io.*;
import java.net.*;
import java.util.*;

public class ListenerThread implements Runnable {

	private ArrayList<User> clientList = new ArrayList<User>();
	private ArrayList<ServerThread> startedServer = new ArrayList<ServerThread>();

	public ListenerThread() {

	}	

	public void run() {
		InetAddress clientAddr = null;
		try {
		    ServerSocket sersock = new ServerSocket(3000);
		    System.out.println("Listener ready");

		    while(true) {
			Socket sock = sersock.accept();
			clientAddr = sock.getInetAddress();
				for (User u  : clientList) {
					if (u.getAddr().equals(clientAddr)) {
						ServerThread serverThread = new ServerThread(u, sock);
						Thread server = new Thread(serverThread);
						server.start();
						startedServer.add(serverThread);
						break;
					}
				}
		    } 
		} catch (Exception e) {
		    e.printStackTrace();
		}             
	}

	public ServerThread getServer(User client) {
		try {
			for(ServerThread st : startedServer) {
				if(st.getUser().equals(client)) {
					return st;
				}
			}
			Socket sock = new Socket(client.getAddr(), 3000);
			ServerThread st = new ServerThread(client, sock);
			Thread server = new Thread(st);
			server.start();
			startedServer.add(st);
			return st;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}    

	public void addNewConnectedUser(String pseudo, InetAddress addr) {
		clientList.add(new User(pseudo, addr));
	}  

	public boolean isUserRegistered(String pseudo) {
		for (User u : clientList) {
			if (u.getPseudo().equals(pseudo)) {
				return true;
			}
		}
		return false;
	} 

	public ArrayList<User> getClientList() {
		return clientList;
	}            
} 

