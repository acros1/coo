/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author al_cros
 */
import java.net.*;
import java.util.*;
import java.text.*;
import java.io.*;


public class Agent {
    
    public InetAddress systemAddr;
    public static int systemPort;
    private List<String> pseudoList = new ArrayList <String>();
   
    
    public Agent()  {
        
            
    }
    
    public Socket startSession(InetAddress ipDest, int portDest)  throws IOException{
        
       
       return null;
        
    }
    
    /*
    This function allows a user to connect to his/her account with his/her login-passwd
    */
    public static int connection(String login, String passwd) {

        return -1;
    }
    
    public void connect_alert(InetAddress broadcastIP) {
        
    }
    
    public InetAddress userToIP(User user) {
        return null;
    }
    
    public InetAddress getSystemAddr(){
        return this.systemAddr;
    }
    
    public int getSystemPort(){
        return this.systemPort;
    }
    
    public void setPseudoList(List<String> pseudoList){
        this.pseudoList = pseudoList;
    }
    
    
    public boolean isConnected(InetAddress ipDest) throws IOException{
        boolean ret = false;
        int timeout = 100;
        try {
            if(ipDest.isReachable(timeout)){
                ret = true;
            }          
        }   
        catch(IOException e){
            System.out.println(e);
        }
        return ret;
    }
    
    
    
    
     
    
    
    
}
