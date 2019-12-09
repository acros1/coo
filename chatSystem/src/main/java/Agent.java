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
        
            
    }
    
    public Socket startSession(InetAddress ipDest, int portDest)  throws IOException{
        
       
       return null;
        
    }
    
    
    public ArrayList<Message> getHistory(User usr1, User usr2) {
        int idUsr1 = usr1.getId();
        int idUsr2 = usr2.getId();
        String path1 = idUsr1+"-"+idUsr2;
        File file1 = new File("C:\\Users\\Maeln\\Documents\\History\\"+path1);
        if(!file1.exists()){
            System.out.println("No history between" + idUsr1 + " and " + idUsr2);        
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e){
            
        }
        return null;
    }
    public int setInHistory(Message msg) throws IOException{
        int ret = -1;
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss \t").format(Calendar.getInstance().getTime());
        List<User> ListUsers = msg.getUsers();
        User usr1 = ListUsers.get(0);
        User usr2 = ListUsers.get(1);
        int idUsr1 = usr1.getId();        
        int idUsr2 = usr2.getId();
        String direct_content =timeStamp + idUsr1 + " -> " + idUsr2 + " --- "+ msg.getContent() +"\n";
        String recep_content = timeStamp + idUsr2 + " <- " + idUsr1 + " --- "+ msg.getContent() +"\n";
        String path1 = idUsr1+"-"+idUsr2;
        String path2 = idUsr2+"-"+idUsr1;
        File file1 = new File("C:\\Users\\Maeln\\Documents\\History\\"+path1);
        File file2 = new File("C:\\Users\\Maeln\\Documents\\History\\"+path2);
         // if file doesnt exists, then create it
        if (!file1.exists() ^ !file2.exists()) {
            file1.createNewFile();
            file2.createNewFile(); 
        }
        try{
            FileWriter fw1 = new FileWriter(file1, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write(direct_content);
            bw1.close();
            
            FileWriter fw2 = new FileWriter(file2, true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write(recep_content);
            bw2.close();
        }
        catch(IOException e){
            System.out.println("fail ecriture");
            System.out.println(e);
        }
            
        
 
        return ret;
    }
    /*
    Function to create a User with a specific login and password, and these login/passwd are put in the login_passwd file located in Server directory
    */
    public static void createUser(String login, String passwd) throws IOException{
        File file1 = new File("C:\\Users\\Maeln\\Documents\\Server\\login_passwd");
        if(!file1.exists()){     
            file1.createNewFile();
        }
        
        try {
            FileWriter fw1 = new FileWriter(file1, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write(login + "\n" + passwd +"\n\n");
            bw1.close();
        }
        catch(IOException e){
            
        }
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
    
    public static boolean isPasswdValid(String passwd, int pos){
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Users");
        if(!file.exists()){     
            System.err.println("No Users cretaed yet");
        }
        boolean ret = false;
        int tmp = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while(tmp != pos){
                line = br.readLine();
                System.out.println(line);
                tmp++;
            }
            if(br.readLine().equals(passwd)){
               
                ret = true;
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        return ret;
    }
}
