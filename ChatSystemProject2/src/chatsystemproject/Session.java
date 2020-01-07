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
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.JFrame;

public class Session {
    
    User usr1;
    User usr2;
    ClientThread ClThread;
    SessionWindow sW;
    
    public Session(User usr1, User usr2,ClientThread clientThread){
        this.usr1 = usr1;
        this.usr2 = usr2;
        this.ClThread = clientThread;
        this.sW = new SessionWindow();
        this.sW.setVisible(true);
        this.sW.setLocationRelativeTo(null);
        this.sW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
    
    public ArrayList<Message> getHistory() {
        int idUsr1 = this.usr1.getId();
        int idUsr2 = this.usr2.getId();
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
    
    public SessionWindow getSessionWindow(){
        return this.sW;
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
    
}
