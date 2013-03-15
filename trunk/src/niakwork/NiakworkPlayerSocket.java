package niakwork;

import java.net.Socket;

public class NiakworkPlayerSocket extends Socket {
	
	private Niakwork niakwork;
	private Socket socket;
	
	
	
	public NiakworkPlayerSocket(Niakwork niakwork, Socket socket) {
		super();
		this.niakwork = niakwork;
		this.socket = socket;
	}

}
