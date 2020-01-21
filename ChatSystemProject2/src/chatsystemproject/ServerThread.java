package chatsystemproject;

import Interface.*;
import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private User client = null;
	private Socket sock = null; // client socket
	private PrintWriter pwrite = null;
        private SessionWindow WindowSession = null;
        private ClientThread clientThread = null;

	public ServerThread(User client, Socket socket) {
		this.client = client;
		this.sock = socket;
	}	
        
        public ServerThread(User client, Socket socket, SessionWindow WindowSession, ClientThread clientThread) {
		this.client = client;
		this.sock = socket;
                this.WindowSession = WindowSession;
                this.clientThread = clientThread;
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
                                    if(receiveMessage.substring(0, 4).equals("EXIT|")){
                                        WindowSession.addMessage(client.getPseudo() + " disconnected... Session ended");
                                        this.WindowSession.getSendButton().setVisible(false);
                                       
                                    }
                                    else{
                                        if(this.WindowSession.isVisible()){
                                            WindowSession.addMessage(receiveMessage);
                                            WindowSession.setVisible(true);
                                            System.out.println(client.getPseudo() + " > " + receiveMessage);  
                                        }
                                        else{
                                            WindowSession.setVisible(true);
                                            WindowSession.addMessage(receiveMessage);
                                            WindowSession.setVisible(true);
                                            System.out.println(client.getPseudo() + " > " + receiveMessage);
                                        }
                                    }
                                                 
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
        
        public synchronized void exitMessage() throws IOException {
                String msg = "Exit|"+this.clientThread.getMainUserPseudo();
		pwrite.println(msg);
		pwrite.flush();
                this.sock.close();
	}

	public User getUser() {
		return this.client;
	}
        
        public Socket getSocket(){
            return this.sock;
        }
        
        public void setSocket(Socket sock){
            this.sock = sock;
            WindowSession.addMessage(client.getPseudo() + "Connected..");
            this.WindowSession.getSendButton().setVisible(true);
            this.run();
        }
} 
