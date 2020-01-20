
package chatsystemproject;

import Interface.applicationWindow;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {
    
    //private User mainUser = null;
    private String pseudo = null;
    private String login = null;

    private ListenerThread listenerThread = null;
    private UDPListener udpListener = null;

    private DatagramPacket outPacket = null;
    private DatagramSocket dgramSocket = null;

    private Scanner scan = new Scanner(System.in);
    private String input = null;

    private applicationWindow aW = null;


    public ClientThread(String pseudo, String login) {
        this.pseudo = pseudo;
        this.login = login;

        this.listenerThread = new ListenerThread(this);
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
            this.broadcastPseudo();//firstly we get the other users pseudo !

            // Asking user which action realise
        } catch (IOException e) {
                e.printStackTrace();
        }            
    }



    public void broadcastPseudo() {
            try {
                // Need to send login, it's the only way to find history (because login is the same every connection) 
                String data = this.getMainUserPseudo() + "|" + this.login;
                this.outPacket = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("255.255.255.255"), 4000);
                this.dgramSocket.send(this.outPacket);
            } catch ( IOException e ) {
                    e.printStackTrace();
            }
    }

    public void deletePseudo(){
        try {
            String delete = "#" + this.getMainUserPseudo();
            this.outPacket = new DatagramPacket(delete.getBytes(), delete.length(), InetAddress.getByName("255.255.255.255"), 4000);
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
            return this.pseudo;
    }

    public void setMainUserPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public ListenerThread getMainSystem(){
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
                    String oldPseudo = "#" + this.getMainUserPseudo();
                    this.setMainUserPseudo(pseudo);
                    this.aW.updatePseudo();
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
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
} 
