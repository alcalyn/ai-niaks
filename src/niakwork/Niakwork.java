package niakwork;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Partie;

public class Niakwork implements Runnable {
	
	public static final String niawkork_version = "NIAKWORK/1.0";
	public static int port = 45678;
	
	
	private Partie partie;
	private boolean enabled = false;
	private ServerSocket server = null;
	private Thread thread = null;
	
	
	public Niakwork(Partie partie) {
		this.partie = partie;
	}
	
	
	@Override
	public void run() {
		try {
			while(true) {
				Socket socket = server.accept();
				
				NiakworkAuth niakwork_auth = new NiakworkAuth(this, socket);
				niakwork_auth.start();
			}
		} catch (InterruptedIOException e) {
			System.out.println("Thread bien interrompu");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void notifyAuthentifiedClient(Socket socket) {
		System.out.println("Client authentified : "+socket.getInetAddress());
	}
	
	
	public Socket searchForHost(String ip) {
		try {
			Socket socket = new Socket(ip, port);
			
			// TODO envoi entete protocol
			
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void enable() {
		if(!enabled) {
			try {
				server = new ServerSocket(port);
				enabled = true;
				thread = new Thread(this);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
				server = null;
				enabled = false;
			}
		}
	}
	
	public void disable() {
		if(enabled) {
			try {
				thread.interrupt();
				thread = null;
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				server = null;
			}
			
			enabled = false;
		}
	}
	
	
	
	
	
	public boolean isEnabled() {
		return enabled;
	}



	
	
}
