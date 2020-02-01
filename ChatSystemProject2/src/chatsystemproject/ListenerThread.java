/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystemproject;

import Interface.SessionWindow;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Maeln
 */
public class ListenerThread implements Runnable {
    
    private ArrayList<User> clientList = new ArrayList<User>();
    private ArrayList<ServerThread> startedServer = new ArrayList<ServerThread>();
    private ClientThread clientThread = null;
    
    public ListenerThread(ClientThread clientThread) {
            this.clientThread = clientThread;
    }	

    public void run() {
        InetAddress clientAddr = null;
        try {
            ServerSocket sersock = new ServerSocket(3000);
            System.out.println("Listener ready");

            while(true) {
                Socket sock = sersock.accept();
                clientAddr = sock.getInetAddress();
                ArrayList<Session> sessionStarted = this.clientThread.getApplicationWindow().getSessionStarted();

                    for (User u  : clientList) {
                        if (u.getAddr().equals(clientAddr)) {
                            for(Session session : sessionStarted){
                                if(session.getUser2().equals(u.getPseudo())){
                                    System.out.println("affichage sessionWindow");
                                    session.setVisibleSessionWindow();
                                    break;
                                }
                            }
                            System.out.println("getServer");
                            getServer(u,null);
                            /*ServerThread serverThread = new ServerThread(u, sock, this.clientThread,new SessionWindow(u,this.clientThread,this.clientThread.getDB()));
                            Thread server = new Thread(serverThread);
                            server.start();
                            startedServer.add(serverThread);*/
                            break;
                        }
                    }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }             
    }

    public ServerThread getServer(User client, Session session) {
            try {
                    for(ServerThread st : startedServer) {
                            if(st.getUser().equals(client)) {
                                    System.out.println("le serveur existe je le renvoie");
                                    return st;
                            }
                    }
                    if(session == null){
                        System.out.println("Session doesn't exists, creation of one");
                        Session sess = new Session(client,this.clientThread,null);
                        Socket sock = new Socket(client.getAddr(), 3000);
                        ServerThread st = new ServerThread(client, sock, this.clientThread,sess);
                        Thread server = new Thread(st);
                        server.start();
                        startedServer.add(st);
                        return st;
                    }
                    else{
                        System.out.println("Session exists, creation of of server only");
                        Socket sock = new Socket(client.getAddr(), 3000);
                        ServerThread st = new ServerThread(client, sock, this.clientThread,session);
                        Thread server = new Thread(st);
                        server.start();
                        startedServer.add(st);
                        return st;  
                    }
                    
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return null;
    }    

    public void addUser(String pseudo, String login, InetAddress addr) {
            clientList.add(new User(pseudo, login, addr));
    }

    public void deleteUser(String pseudo) {
            for (int i = 0 ; i < clientList.size() ; i++) {
                    if ( clientList.get(i).getPseudo().equals(pseudo) ) {
                            clientList.remove(i);
                    }
            }
            this.clientThread.getApplicationWindow();

    }

    public boolean isUserExist(String pseudo) {
            for (User u : clientList) {
                    if (u.getPseudo().equals(pseudo)) {
                            return true;
                    }
            }
            return false;
    }
    
    public User getUserByPseudo(String pseudo) {
            for (User u : clientList) {
                    if (u.getPseudo().equals(pseudo)) {
                            return u;
                    }
            }
            return null;
    }

    public ArrayList<User> getClientList() {
            return clientList;
    }

    public ArrayList<ServerThread> getServerStarted(){
        return this.startedServer;
    }
 }

