package niakwork;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import exceptions.NiaksException;

public class NiakworkServer extends Thread {
	
	
	private Niakwork niakwork;
	private ServerSocket server = null;
	private int [] ports;
	private int port = -1;
	
	
	public NiakworkServer(Niakwork niakwork, int [] ports) throws NiaksException {
		super("Thread-NiakworkServer");
		this.niakwork = niakwork;
		this.ports = ports;
		initServer();
		start();
	}
	
	
	private void initServer() throws NiaksException {
		for (int p : ports) {
			if(this.port == -1) {
				try {
					System.out.println("Server > Try to start at port "+p+"...");
					server = new ServerSocket(p);
					this.port = p;
					System.out.println("Server > starting server at port "+p);
				} catch (IOException e) {
					System.out.println("Server > port "+p+" already used");
					server = null;
					continue;
				}
			} else {
				break;
			}
		}
		
		if(this.port == -1) {
			throw new NiaksException("All ports are already used");
		}
	}
	
	
	public int getPort() {
		return port;
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
