package chatsystemproject;

import Interface.*;
import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private User client = null;
	private Socket sock = null; // client socket
	private PrintWriter pwrite = null;
        private SessionWindow sessionWindow = null;
        private ClientThread clientThread = null;
        private Session session = null;
        
        private boolean connection = true;

	public ServerThread(User client, Socket socket) {
		this.client = client;
		this.sock = socket;
	}	
        
        public ServerThread(User client, Socket socket, ClientThread clientThread) {
		this.client = client;
		this.sock = socket;
                this.clientThread = clientThread;
	}
        
        public ServerThread(User client, Socket socket, ClientThread clientThread, Session session) {
		this.client = client;
		this.sock = socket;
                this.clientThread = clientThread;
                this.session = session;
                this.sessionWindow = session.getSessionWindow();

	}
        
        

	public void run() {
		try {
                    if(this.sessionWindow == null){
                      this.sessionWindow = new SessionWindow(client,this.clientThread,this.clientThread.getDB()); 
                      sessionWindow.setLocationRelativeTo(null);
                    }
		    // sending to client (pwrite object)
		    OutputStream ostream = sock.getOutputStream(); 
		    pwrite = new PrintWriter(ostream, true);

		    // receiving from server ( receiveRead  object)
		    InputStream istream = sock.getInputStream();
		    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		    String receiveMessage;              
		    while(connection) {
                        
				if((receiveMessage = receiveRead.readLine()) != null) {
                                    System.out.println("Message reçu : " + receiveMessage);
                                    if(receiveMessage.length() == 5 && receiveMessage.equals("EXIT|")){
                                        this.sessionWindow.addMessage(client.getPseudo() + " disconnected... Session ended");
                                        this.sessionWindow.getSendButton().setVisible(false);
                                       
                                    }
                                    else{
                                        if(this.sessionWindow.isVisible()){
                                            sessionWindow.addMessage(receiveMessage);
                                            System.out.println(client.getPseudo() + " > " + receiveMessage);  
                                        }
                                        else{
                                            this.sessionWindow.setVisible(true);
                                            sessionWindow.addMessage(receiveMessage);
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
                //this.sock.close(); //really useful ?
                
	}

	public User getUser() {
		return this.client;
	}
        
        public Socket getSocket(){
            return this.sock;
        }
        
        public void setSocket(Socket sock){
            this.sock = sock;
            sessionWindow.addMessage(client.getPseudo() + "Connected..");
            this.sessionWindow.getSendButton().setVisible(true);
            this.run();
        }
        
        public void deconnexion() {
            this.connection = false;
        }
        
        public void setsessionWindow(SessionWindow sessionWindow) {
            this.sessionWindow = sessionWindow;
        }
       
        public void setSession(Session session){
            this.session = session;
        }
} 
