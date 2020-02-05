package chatsystemproject;

import Interface.*;
import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private User client = null;
	private Socket sock = null; // client socket
	private PrintWriter pwrite = null;
        private OutputStream outputstream = null;
        private SessionWindow sessionWindow = null;
        private ClientThread clientThread = null;

        public final static int FILE_SIZE = 6022386;
        private boolean connection = true;

	public ServerThread(User client, Socket socket) {
            System.out.println("ServerThread created 1er constru");
		this.client = client;
		this.sock = socket;
	}	
        
        public ServerThread(User client, Socket socket, ClientThread clientThread) {
            System.out.println("ServerThread created 2e constru");
		this.client = client;
		this.sock = socket;
                this.clientThread = clientThread;
	}
        
        public ServerThread(User client, Socket socket, ClientThread clientThread, SessionWindow session) {
                
                if(session != null){
                   System.out.println("ServerThread created");
                    this.client = client;
                    this.sock = socket;
                    this.clientThread = clientThread; 
                    this.sessionWindow = session;
                }
                else if(session == null){
                    System.out.println("ServerThread + Session created");
                    this.client = client;
                    this.sock = socket;
                    this.clientThread = clientThread;
                    this.sessionWindow = new SessionWindow(client,this.clientThread,this.clientThread.getDB(),this);
                    
                }
        }

	public void run() {
		try {
                    /*if(this.sessionWindow == null){
                      this.sessionWindow = new SessionWindow(client,this.clientThread,this.clientThread.getDB()); 
                      sessionWindow.setLocationRelativeTo(null);
                    }*/
		    // sending to client (pwrite object)
                    System.out.println("Running st");
		    this.outputstream = sock.getOutputStream(); 
                    byte[] buffer = new byte[FILE_SIZE];
		    //this.pwrite = new PrintWriter(ostream, true);
		    // receiving from server ( receiveRead  object)
		    InputStream istream = sock.getInputStream();
                    
		    //BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		    String receiveMessage;  
                    System.out.println("Waiting message....");
                    System.out.println("socket connecté ? : "+this.sock.isConnected());
                    System.out.println("socket addr ? : "+this.sock.getInetAddress());
                    System.out.println("socket port ? : "+this.sock.getLocalPort());
                    System.out.println("socket addr local ? : "+this.sock.getLocalAddress());
                    System.out.println("addr dest ? : "+this.client.getAddr());
                    System.out.println(connection);
		    while(connection) {
                        int ret = istream.read(buffer,0,FILE_SIZE);
                        if(new String(buffer,0,2).equals("S:")){
                            receiveMessage = new String(buffer,2,ret -2);
                            System.out.println("Message reçu : " + receiveMessage);
                                    if(receiveMessage.length() == 5 && receiveMessage.equals("EXIT|")){
                                        this.sessionWindow.addMessage(client.getPseudo() + " disconnected... Session ended");
                                        this.sessionWindow.getSendButton().setVisible(false);
                                        this.deconnexion();
                                       
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
                        else if(new String(buffer,0,2).equals("P:")){
                            
                        }
                        else if(new String(buffer,0,2).equals("I:")){
                            
                        }
                        else if(new String(buffer,0,2).equals("J:")){
                            
                        }
				
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} 
                System.out.println("fin du run");
	}

	public synchronized void writeMessage(String msg) throws IOException {
            String message = "S:" + msg;
            outputstream.write(message.getBytes());
            System.out.println("envoie du message : "+ message);
            System.out.println("message envoyé en théorie");
	} 
        
        public synchronized void exitMessage() throws IOException {
                String msg = "Exit|"+this.clientThread.getMainUserPseudo();
		pwrite.println(msg);
		pwrite.flush();
                //this.sock.close(); //really useful ?
                
	}
        
        public synchronized void sendPdfFile(File myFile) throws IOException{
            String ent = "P:";
            byte [] head = ent.getBytes();
            byte [] mybytearray  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( head );
            outputStream.write( mybytearray );
            byte message[] = outputStream.toByteArray( );
            OutputStream os = sock.getOutputStream();
            os.write(message,0,message.length);
            os.flush();
            System.out.println("Done.");
        }
        public synchronized void sendPngFile(File myFile) throws IOException{
            String ent = "I:";
            byte [] head = ent.getBytes();
            byte [] mybytearray  = new byte [(int)myFile.length()+2];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = sock.getOutputStream();
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( head );
            outputStream.write( mybytearray );
            byte message[] = outputStream.toByteArray( );
            
            os.write(message,0,message.length);
            os.flush();
            System.out.println("Done.");
        }
        public synchronized void sendJpgFile(File myFile) throws IOException{
            String ent = "J:";
            byte [] head = ent.getBytes();
            byte [] mybytearray  = new byte [(int)myFile.length()+2];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = sock.getOutputStream();
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( head );
            outputStream.write( mybytearray );
            byte message[] = outputStream.toByteArray( );
            
            os.write(message,0,message.length);
            os.flush();
            System.out.println("Done.");
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
        public void sendDeconnexion() throws IOException{
            
            this.writeMessage("EXIT|");
            this.sock.close();
        }
        
        public void setsessionWindow(SessionWindow sessionWindow) {
            this.sessionWindow = sessionWindow;
        }
       

} 
