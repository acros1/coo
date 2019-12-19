import java.net.*;

public class User {

	private String pseudo = null;
	private InetAddress addr = null;

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

}
