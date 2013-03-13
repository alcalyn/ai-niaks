package niakwork;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NiakworkServer extends Thread {
	
	
	private Niakwork niakwork;
	private ServerSocket server = null;
	private int port;
	
	
	public NiakworkServer(Niakwork niakwork, int port) {
		super("Thread-NiakworkServer");
		this.niakwork = niakwork;
		this.port = port;
		initServer();
		start();
	}
	
	
	private void initServer() {
		try {
			System.out.println("Server > starting server");
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	


	@Override
	public void run() {
		try {
			while(true) {
				Socket socket = null;
				
				socket = server.accept();
				
				System.out.println("Server > potential client found");
				
				new NiakworkAuth(niakwork, socket);
			}
		} catch (InterruptedIOException e) {
			System.out.println("Thread bien interrompu");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server = null;
		interrupt();
	}


}
