/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author al_cros
 */
package chatsystemproject;

import Interface.SessionWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.JFrame;

public class Session {
  /*  
    private User mainUser = null;
    private User user2 = null;
    private ClientThread clientThread = null;
    private SessionWindow sW = null;
    private ServerThread ServerThread = null;
    
    public Session(User user2, ClientThread clientThread){
        this.mainUser = new User(clientThread.getMainUserPseudo(), clientThread.getLogin(), null);
        this.user2 = user2;
        this.clientThread = clientThread;
        this.sW = new SessionWindow(user2, this.clientThread,this.clientThread.getDB());
        this.ServerThread = this.clientThread.getMainSystem().getServer(user2,this);       
        this.ServerThread.setSession(this);
        this.sW.setServerThread(ServerThread);
        this.sW.setLocationRelativeTo(null);
        this.sW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.sW.setVisible(true);
        
    }
    
    public Session(User user2, ClientThread clientThread,ServerThread st){
        this.mainUser = new User(clientThread.getMainUserPseudo(), clientThread.getLogin(), null);
        this.user2 = user2;
        this.clientThread = clientThread;
        this.sW = new SessionWindow(user2, this.clientThread,this.clientThread.getDB());
        this.sW.setLocationRelativeTo(null);
        this.sW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.sW.setVisible(true);
        
    }
    
    
   
    public void setVisibleSessionWindow(){
        this.sW.setVisible(true);
    }
    
    
    public boolean isVisibleSessionWindow(){
        return this.sW.isVisible();
    }
    
    
    public SessionWindow getSessionWindow(){
        return this.sW;
    }
    
    public User getMainUser() {
        return mainUser;
    }

    public User getUser2() {
        return user2;
    }
    
    public void setServerThread(ServerThread st){
        System.out.println("setting st :"+ st +" and session");
        
        this.ServerThread = st;
        st.setSession(this);
        this.sW.setServerThread(st);
    }

    public ServerThread getServerThread() {
        return ServerThread;
    }
    
 

  */  
    
}
