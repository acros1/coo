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
import java.lang.Object;
import java.text.*;
import java.io.*;
import java.nio.file.*;

public class Agent {
    
    public InetAddress systemAddr;
    public static int systemPort;
    private List<String> pseudoList = new ArrayList <String>();
    
    public Agent()  {
        /*try {
            this.systemAddr = InetAddress.getLocalHost(); // get the local agent address
        } catch (UnknownHostException err_getting_lhost_addr) {
            System.out.println("Err while getting lhost address");
        }
        ServerSocket ss = null;
        this.systemPort = 2000;
        try {
            ss = new ServerSocket(systemPort);
        } catch (Exception err_ServerSocket){
            System.out.println("err_SocketServer");
            System.exit(1);
        }
        Socket link = null;
        try {
            link = ss.accept();
        } catch(Exception err_accept) {
            //Catching accept error
            System.out.println("err_accept");
            System.exit(1);
        }
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(
                new InputStreamReader(
                    link.getInputStream()));
            out = new PrintWriter(
                link.getOutputStream(),true);
        } catch(IOException err_buffer){
                System.out.println("err_buffer");
                System.exit(1);
        }*/
            
    }
    
    public Socket startSession(InetAddress ipDest, int portDest)  throws IOException{
        
       /* Socket link = null;
        try {
            link = new Socket (ipDest, portDest);
        }catch(Exception err_socketCreate) {
            System.out.println("err_socketCreate");
            System.exit(1);
        }

        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(
                new InputStreamReader(
                    link.getInputStream()));
            out = new PrintWriter(
                link.getOutputStream(),true);
        }catch(Exception err_buffer) {
            System.out.println("err_buffer");
            System.exit(1);
        }                    
        // out.println("awaiting data...");
        String input = in.readLine();
        System.out.println(input);
        return link;*/
       return null;
        
    }
    
    
    public ArrayList<Message> getHistory(User usr1, User usr2) {
        int Id1 = usr1.getId();
        int Id2 = usr2.getId();
        
        return null;
    }
    public int setInHistory(Message msg) throws IOException{
        int ret = -1;
        String pattern = "dd/MM/yyyy HH:mm:ss";

        // Create an instance of SimpleDateFormat used for formatting 
        // the string representation of date according to the chosen pattern
        DateFormat df = new SimpleDateFormat(pattern);
        String DateString= df.format(msg.date);
        
        
        List<User> ListUsers = msg.getUsers();
       
        User usr1 = ListUsers.get(0);
        User usr2 = ListUsers.get(1);
        
        int idUsr1 = usr1.getId();
        int idUsr2 = usr2.getId();
        
        String content = "Date :" + DateString + " " + usr1 + " : "+msg.getContent();
        String path1 = idUsr1+"-"+idUsr2;
        File file1 = new File("/document/History/"+path1); 
         // if file doesnt exists, then create it
        if (!file1.exists()) {
            file1.createNewFile();   
        }
        try{
            FileWriter fw = new FileWriter(file1, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
            
        
 
        return ret;
    }
    
    public int connection(String login, String passwd) {
        int ret = 0;
        
        return ret;
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
    public boolean isPseudoValid(String pseudo){
        boolean ret = true;
        Iterator<String> itr = pseudoList.iterator();
        while ( itr.hasNext() ) {
          if(  itr.next().equals(pseudo)  ){
              ret = false;
          }
        }
        return ret;
    }
}
