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
    
    private User mainUser = null;
    private User user2 = null;
    private ClientThread clientThread = null;
    private SessionWindow sW = null;
    
    public Session(User user2, ClientThread clientThread){
        this.mainUser = new User(clientThread.getMainUserPseudo(), clientThread.getLogin(), null);
        this.user2 = user2;
        this.clientThread = clientThread;
        System.out.println("Session créée");
        
        
        System.out.println("création fenetre : ");
        this.sW = new SessionWindow(user2, this.clientThread,this);
        System.out.println("Session créée normalement...");
        this.sW.setLocationRelativeTo(null);
        this.sW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
    
    
   
    
    public SessionWindow getSessionWindow(){
        return this.sW;
    }
    
    public User getUser(){
        return this.user2;
    }
    
    public void endSession(ServerThread st){
        this.clientThread.getApplicationWindow().endSession(this,st);
    }
    
    
}
