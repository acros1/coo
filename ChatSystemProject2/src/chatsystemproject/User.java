package chatsystemproject;

import java.net.*;

public class User {

    private String pseudo = null;
    private String login = null;
    private InetAddress addr = null;
    private int id;

    /*public User(String pseudo, InetAddress addr) {
        this.pseudo = pseudo;
        this.addr = addr;
    }*/

    public User(String pseudo, String login, InetAddress addr) {
        this.pseudo = pseudo;
        this.login = login;
        this.addr = addr;
    }

    public String getPseudo() {
        return this.pseudo;
    }
    
    public String getLogin() {
        return this.login;
    }

    public InetAddress getAddr() {
        return this.addr;
    }
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public int getId(){
        return this.id;
    }

}
