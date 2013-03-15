package niakwork;

import java.net.Socket;

public class NiakworkHostSocket extends Socket {
	
	private Niakwork niakwork;
	private Socket socket;
	
	
	
	public NiakworkHostSocket(Niakwork niakwork, Socket socket) {
		super();
		this.niakwork = niakwork;
		this.socket = socket;
	}
	
	

}
