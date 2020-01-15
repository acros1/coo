package chatsystemproject;

import java.net.*;

public class User {

	private String pseudo = null;
	private InetAddress addr = null;
        private int id;

	/*public User(String pseudo, InetAddress addr) {
		this.pseudo = pseudo;
		this.addr = addr;
	}*/
        
        public User(String pseudo, InetAddress addr) {
		this.pseudo = pseudo;
		this.addr = addr;
	}

	public String getPseudo() {
		return this.pseudo;
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
