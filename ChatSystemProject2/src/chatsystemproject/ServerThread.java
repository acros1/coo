package chatsystemproject;

import Interface.*;
import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private User client = null;
	private Socket sock = null; // client socket
	private PrintWriter pwrite = null;
        private SessionWindow WindowSession = null;

	public ServerThread(User client, Socket socket) {
		this.client = client;
		this.sock = socket;
	}	
        
        public ServerThread(User client, Socket socket,SessionWindow WindowSession) {
		this.client = client;
		this.sock = socket;
                this.WindowSession = WindowSession;
	}

	public void run() {
		try {
		    // sending to client (pwrite object)
		    OutputStream ostream = sock.getOutputStream(); 
		    pwrite = new PrintWriter(ostream, true);

		    // receiving from server ( receiveRead  object)
		    InputStream istream = sock.getInputStream();
		    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		    String receiveMessage;              
		    while(true) {
				if((receiveMessage = receiveRead.readLine()) != null) {
                                        WindowSession.addMessage(receiveMessage);
                                        WindowSession.setVisible(true);
					System.out.println(client.getPseudo() + " > " + receiveMessage);         
				}
		    }  
		} catch (Exception e) {
		    e.printStackTrace();
		}             
	}

	public synchronized void writeMessage(String msg) {
		pwrite.println(msg);
		pwrite.flush();
	} 

	public User getUser() {
		return this.client;
	}                 
} 
