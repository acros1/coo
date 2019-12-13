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
    
    
    public int sendMessage(Message msg) throws IOException{
        try {
            setInHistory(msg);
        }
        catch(IOException e){
            System.out.println("error write");
            System.out.println(e);
        }
        return 0;
    }
    
     public int isPseudoValid(String pseudo,File file) throws IOException {
        int ret = -1;
        if(!file.exists()){
            System.out.println("No Users created yet");    
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {                 
                if(line.equals(pseudo)){                    
                    ret=i;
                }
                i++;
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
        return ret;
    }
    
    public static int isLoginValid(String login){
        int ret = 0;
   
        
        File file = new File("C:\\Users\\Maeln\\Documents\\server\\Users");
        if(!file.exists()){
            System.out.println("No Users created yet");    
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {                 
                if((i%4) == 0){                    
                    if(line.equals(login)){
                        System.out.println("Login : " + login +" = line : "+ line);
                        ret=i;
                    }        
                }
                i++;         
            }
        }
        catch(IOException e){
             System.out.println(e);
        }
     
        return ret;
    }
    
    public static int isPasswdValid(String passwd, int pos){
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Users");
        if(!file.exists()){     
            System.err.println("No Users cretaed yet");
        }
        int ret = -1;
        int tmp = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while(tmp != pos){
                line = br.readLine();
                System.out.println(line);
                tmp++;
            }
            if(br.readLine().equals(passwd)){
               
                ret = Integer.parseInt(br.readLine());
            }
            
        }
        catch(IOException e){
            System.out.println(e);
        }
        return ret;
    }
}
