package niakwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Partie;

public class Niakwork {
	
	public static final String niawkwork_version = "NIAKWORK/1.0";
	public static int login_timeout = 650;
	public static int port = 23456;
	
	
	private Partie partie;
	private boolean enabled = false;
	private NiakworkServer server = null;
	
	
	public Niakwork(Partie partie) {
		this.partie = partie;
	}
	
	
	
	public void notifyAuthentifiedClient(Socket socket) {
		System.out.println("Client authentified : "+socket.getInetAddress());
	}
	
	public void notifyAuthentifiedServer(Socket socket) {
		System.out.println("Server authentified : "+socket.getInetAddress());
	}
	
	
	public void connectTo(InetSocketAddress endpoint) {
		new NiakworkLogin(this, endpoint);
	}
	
	
	public void searchHost() {
		String host_adress = null;
		
		try {
			host_adress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("Niakwork > searching host except me ("+host_adress+") ...");
		
		for(int i=1;i<=254;i++) {
			String ip = "192.168.0."+i;
			
			if((host_adress == null) || !ip.equals(host_adress)) {
				connectTo(new InetSocketAddress(ip, port));
			}
		}
	}
	
	
	private void startServer() {
		server = new NiakworkServer(this, port);
	}
	
	public void enable() {
		if(!enabled) {
			startServer();
			enabled = true;
		}
	}
	
	public void disable() {
		if(enabled) {
			server.close();
			server = null;
			
			enabled = false;
		}
	}
	
	
	
	
	
	public boolean isEnabled() {
		return enabled;
	}


	public static BufferedWriter getBW(Socket socket) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public static BufferedReader getBR(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	
}
