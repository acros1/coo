
package chatsystemproject;

import Interface.applicationWindow;
import Database.Connect;
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
                System.out.println("Broadcasting my pseudo");
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
                    try {
                            this.outPacket = new DatagramPacket(oldPseudo.getBytes(), oldPseudo.length(), InetAddress.getByName("255.255.255.255"), 4000);
                            this.dgramSocket.send(this.outPacket);
                    } catch ( IOException e ) {
                            e.printStackTrace();
                    }
                    this.setMainUserPseudo(newPseudo);
                    this.aW.updatePseudo();
                    // Broadcasting pseudo
                    this.broadcastPseudo();
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public Connect getDB() {
        return this.aW.getDB();
    }
        
} 
