import java.net.*;

public class User {

	private String name = null;
	private InetAddress addr = null;

	public User(String name, InetAddress addr) {
		this.name = name;
		this.addr = addr;
	}

	public String getName() {
		return this.name;
	}

	public InetAddress getAddr() {
		return this.addr;
	}

}
