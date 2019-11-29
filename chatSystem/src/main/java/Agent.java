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
import java.io.*;

public class Agent implements Runnable{
    
    public InetAddress systemAddr;
    public static int systemPort;
    
    public Agent()  {
        try {
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
        }
            
    }
    
    public Socket startSession(InetAddress ipDest, int portDest)  throws IOException{
        
        Socket link = null;
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
        return link;
        
    }
    
    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    
    public void closeSession(Socket link) {
        try {
            link.close();
        }catch(Exception err_close) {
            System.out.println("err_close");
            System.exit(1);
        } 
    }
    
    public ArrayList<Message> getHistory(User usr1, User usr2) {
        return null;
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
}
