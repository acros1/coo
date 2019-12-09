/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author al_cros
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class User {
    
    public String pseudo;
    public String login;
    public String passwd;
    public int id;
    public Agent agent;
    
    public User(Agent agent, int id) {
        this.agent = agent; 
        this.id = id; 
        
    }
    
    public int changePseudo(String newPseudo) {
        int ret = -1;
        if(agent.isPseudoValid(newPseudo)){
            this.pseudo = newPseudo;
            ret = 0;
        }
        
        return ret;
    }
    
    public int choosePseudo(String newPseudo) {
        return 0;
    }
    
    public Agent getAgent(){
        return this.agent;
    }
   
    public static User user_Login(Agent agent){
        int pos;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your login : ");
        String login = sc.nextLine();
        System.out.println("Please enter your password : ");
        String passwd = sc.nextLine();
        while(((pos = Agent.isLoginValid(login)) == 0) && !(Agent.isPasswdValid(passwd, pos+1)) ){
            System.out.println("faux");
            System.out.println("Wrong combination login/password please retry..");
            System.out.println("Please enter your login : ");
            login = sc.nextLine();
            System.out.println("Please enter your password : ");
            passwd = sc.nextLine();
        }
        System.out.println("Good");
        int id = 0;
        User user = new User(agent,id);
        return user;
    }
    
    public static int Creation_utilisateur() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a login : ");
        String login = sc.nextLine();
        while(Agent.isLoginValid(login) != 0){
           System.out.println("Login already use...\nPlease choose another one :");
           login = sc.nextLine();
        }
        
        System.out.println("Valid Login \nPlease choose a password :");
        String passwd = sc.nextLine();
        
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Users");
        if(!file.exists()){     
            file.createNewFile();
        }
        
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            int id = getMaxId() + 1;
            bw.write(login + "\n" + passwd +"\n" + id +"\n\n");
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return 0;
    }
    
    public int getId(){
        return this.id;
    }
    
    public static int getMaxId(){
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Users");
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((br.readLine()) != null) {
                i++;
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        int tmp = i/4;
        return tmp;
    }
    
}
    

