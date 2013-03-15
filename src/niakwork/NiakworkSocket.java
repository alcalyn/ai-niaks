package niakwork;

import java.net.Socket;

public abstract class NiakworkSocket extends Socket {
	
	
	protected Niakwork niakwork;
	protected Socket socket;
	
	
	
	public NiakworkSocket(Niakwork niakwork, Socket socket) {
		super();
		this.niakwork = niakwork;
		this.socket = socket;
	}
}
