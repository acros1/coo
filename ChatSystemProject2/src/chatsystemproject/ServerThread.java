package chatsystemproject;

import Interface.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Object.*;
import java.net.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ServerThread implements Runnable {

	private User client = null;
	private Socket sock = null; // client socket
	private PrintWriter pwrite = null;
        private OutputStream outputstream = null;
        private SessionWindow sessionWindow = null;
        private ClientThread clientThread = null;

        public final static int FILE_SIZE = 6022386;
        private boolean connection = true;
	
        
        public ServerThread(User client, Socket socket, ClientThread clientThread) {
		this.client = client;
		this.sock = socket;
                this.clientThread = clientThread;
	}
        
        public ServerThread(User client, Socket socket, ClientThread clientThread, SessionWindow session) {
                
                if(session != null){
                    this.client = client;
                    this.sock = socket;
                    this.clientThread = clientThread; 
                    this.sessionWindow = session;
                }
                else if(session == null){
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
		    this.outputstream = sock.getOutputStream(); 
                    byte[] buffer = new byte[FILE_SIZE];
		    //this.pwrite = new PrintWriter(ostream, true);
		    // receiving from server ( receiveRead  object)
		    InputStream istream = sock.getInputStream();
                    
		    //BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		    String receiveMessage;  
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
                            byte [] PdfBuffer = Arrays.copyOfRange(buffer, 2, buffer.length);
                            try (FileOutputStream fos = new FileOutputStream("Download/FileDownloaded.pdf")) {
                            fos.write(PdfBuffer);   
                            this.sessionWindow.addSystemMessage("\n"+client.getPseudo()+" sent you a pdf.\nPlease find it in the Download folder of this project.");
                            }
                            
                        }
                        else if(new String(buffer,0,2).equals("I:")){
                            byte [] imgBuffer = Arrays.copyOfRange(buffer, 2, buffer.length);
                            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imgBuffer));

  
                             ImageIO.write(image, "png", new File("Download/Imagepng.png"));
                             this.sessionWindow.addSystemMessage("\n"+client.getPseudo()+" sent you a png.\nPlease find it in the Download folder of this project.");
                        }
                        else if(new String(buffer,0,2).equals("J:")){
                            byte [] imgBuffer = Arrays.copyOfRange(buffer, 2, buffer.length);    
                            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imgBuffer));
                            
                            ImageIO.write(image, "jpg", new File("Download/Imagejpg.jpg"));
                            this.sessionWindow.addSystemMessage("\n"+client.getPseudo()+" sent you a jpg.\nPlease find it in the Download folder of this project.");

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
            this.sessionWindow.addSystemMessage("Pdf sent");
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
            this.sessionWindow.addSystemMessage("Png sent");
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
            this.sessionWindow.addSystemMessage("Jpg sent");
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
            String message = "S:EXIT|";
            outputstream.write(message.getBytes());
            this.sock.close();
        }
        
        public void setsessionWindow(SessionWindow sessionWindow) {
            this.sessionWindow = sessionWindow;
        }
       

} 
