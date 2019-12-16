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
import java.net.InetAddress;
import java.util.*;


public class User {
    
    public String pseudo;
    public String login;
    public String passwd;
    public int id;
    public Agent agent;
    private InetAddress addr;
    
    public User(Agent agent, int id) {
        this.agent = agent; 
        this.id = id; 
        System.out.println("Je suis un user créé avec l'id : " + id);        
    }
    
    public User(String pseudo, InetAddress addr) {
        this.pseudo = pseudo;
        this.addr = addr;
    }
    
    public int changePseudo() throws IOException {
        int ret = -1;
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Users");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a pseudonym : ");
        String newPseudo = sc.nextLine();
        while((this.isPseudoValid(newPseudo,file))== -1){
            
        }
        if((this.isPseudoValid(newPseudo,file))== -1){
            this.pseudo = newPseudo;
            ret = 0;
        }
        
        return ret;
    }
    
    public int choosePseudo() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a pseudonym : ");
        String pseudo = sc.nextLine();
        File file = new File("C:\\Users\\Maeln\\Documents\\Server\\Pseudo");
        if(!file.exists()){     
            file.createNewFile();
        }
        
        while((this.isPseudoValid(pseudo,file)) != -1){
            System.out.println("Pseudonym already taken");
            System.out.println("Please choose another pseudonym : ");
            pseudo = sc.nextLine();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pseudo + "\n\n");
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return 0;
    }
    
    public Agent getAgent(){
        return this.agent;
    }
   /*
    Function to login to a created user, it supposed to have an account (create_user)
    */
    public static User userLogin(){
        int pos;
        int id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your login : ");
        String login = sc.nextLine();
        System.out.println("Please enter your password : ");
        String passwd = sc.nextLine();
        while( ((pos = User.isLoginValid(login)) == 0) && (User.isPasswdValid(passwd, pos+1) == -1) ){
            System.out.println("faux");
            System.out.println("Wrong combination login/password please retry..");
            System.out.println("Please enter your login : ");
            login = sc.nextLine();
            System.out.println("Please enter your password : ");
            passwd = sc.nextLine();
        }
        id = User.isPasswdValid(passwd, pos+1);
        System.out.println(id);
        System.out.println("Good");
        User user = new User(null,id); // remplacer null par agent
        return user;
    }
    
    
    /* 
    It is the function to create a user, choosing a login and a password,
    it is based on what is typed by the user himself.
    */
    public static int createUser() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a login : ");
        String login = sc.nextLine();
        while(User.isLoginValid(login) != 0){
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
    
    public String getPseudo() {
        return this.pseudo;
    }
    
    public InetAddress getAddr() {
        return this.addr;
    }
    
}
    

