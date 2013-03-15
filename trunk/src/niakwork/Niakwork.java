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

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import exceptions.IllegalMoveNiaksException;

import model.Coup;
import model.CoupListener;
import model.Niaks;

public class Niakwork {
	
	public static final String niawkwork_version = "NIAKWORK/1.0";
	public static int login_timeout = 650;
	public static int port = 23456;
	
	
	private Niaks niaks;
	private NiakworkServer server = null;
	
	private EventListenerList listeners = new EventListenerList();
	
	
	public Niakwork(Niaks niaks) {
		this.niaks = niaks;
	}
	
	
	
	public void notifyAuthentifiedClient(Socket socket) {
		System.out.println("Client authentified : "+socket.getInetAddress());
		for (NiakworkListener listener : listeners.getListeners(NiakworkListener.class)) {
			listener.clientFound(new NiakworkPlayerSocket(this, socket));
		}
	}
	
	public void notifyAuthentifiedServer(Socket socket) {
		System.out.println("Server authentified : "+socket.getInetAddress());
		for (NiakworkListener listener : listeners.getListeners(NiakworkListener.class)) {
			listener.hostFound(new NiakworkPlayerSocket(this, socket));
		}
	}
	
	
	public void connectTo(InetSocketAddress endpoint) {
		new NiakworkLogin(this, endpoint);
	}
	
	
	public void searchHost() {
		String host_adress = null;
		String network_prefix = null;
		
		try {
			host_adress = InetAddress.getLocalHost().getHostAddress();
			
			byte [] raw_ip = InetAddress.getLocalHost().getAddress();
			network_prefix = "";
			
			for (int i=0;i<3;i++) {
				int n = raw_ip[i];
				if(n < 0) n += 256;
				network_prefix += n+".";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("Niakwork > searching host ");
		System.out.println("           from "+network_prefix+"1");
		System.out.println("           to   "+network_prefix+"254");
		System.out.println("           except me ("+host_adress+") ...");
		
		for(int i=1;i<=254;i++) {
			String ip = network_prefix+i;
			
			if((host_adress == null) || !ip.equals(host_adress)) {
				connectTo(new InetSocketAddress(ip, port));
			}
		}
	}
	
	
	public void startServer() {
		server = new NiakworkServer(this, port);
	}
	
	public void stopServer() {
		if(server != null) {
			server.close();
			server = null;
		}
	}
	
	
	
	public void close() {
		stopServer();
	}
	
	public void addNiakworkListener(NiakworkListener listener) {
		listeners.add(NiakworkListener.class, listener);
	}
	
	
	


	public static BufferedWriter getBW(Socket socket) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public static BufferedReader getBR(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	
}
