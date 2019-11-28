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
import java.util.ArrayList;

public class Agent {
    
    public InetAddress systemAddr;
    
    public Agent() {
        try {
            this.systemAddr = InetAddress.getLocalHost(); // get the local agent address
        } catch (UnknownHostException err_getting_lhost_addr) {
            System.out.println("Err while getting lhost address");
        }
            
    }
    
    public void startSession(User usrDest, User usrSrc) {
        
    }
    
    public void closeSession(User usrDest, User usrSrc) {
        
    }
    
    public ArrayList<Message> getHistory(User usr1, User usr2) {
        return null;
    }
}
