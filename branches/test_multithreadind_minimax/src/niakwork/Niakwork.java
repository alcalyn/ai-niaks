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
import java.util.ArrayList;

import model.Niaks;
import exceptions.NiaksException;

public class Niakwork {
	
	
	public static final String niawkwork_version = "NIAKWORK/1.0";
	public static int login_timeout = 650;
	public static int [] ports = new int[] {23456, 23457, 23458};
	
	
	private Niaks niaks;
	private NiakworkServer server = null;
	
	
	private ArrayList<NiakworkHostSocket> hosts = new ArrayList<NiakworkHostSocket>();
	private ArrayList<NiakworkPlayerSocket> clients = new ArrayList<NiakworkPlayerSocket>();
	
	
	public Niakwork(Niaks niaks) {
		this.niaks = niaks;
	}
	
	
	
	public void notifyAuthentifiedClient(Socket socket) {
		System.out.println("Niakwork > client found");
		NiakworkPlayerSocket npsocket = new NiakworkPlayerSocket(this, socket);
		npsocket.querySendPseudo();
		clients.add(npsocket);
	}
	
	public void notifyAuthentifiedServer(Socket socket) {
		System.out.println("Niakwork > server found. Notifying model");
		NiakworkHostSocket nssocket = new NiakworkHostSocket(this, socket);
		hosts.add(nssocket);
		niaks.niakworkServerFound(nssocket, nssocket.getPseudo());
	}
	
	
	public void connectTo(InetSocketAddress endpoint) {
		new NiakworkLogin(this, endpoint);
	}
	public void connectTo(String ip) {
		for (int p : ports) {
			connectTo(new InetSocketAddress(ip, p));
		}
	}
	
	
	public void searchHost() {
		hosts = new ArrayList<NiakworkHostSocket>();
		
		String host_adress = null;
		int host_port = -1;
		String network_prefix = null;
		
		try {
			host_adress = InetAddress.getLocalHost().getHostAddress();
			host_port = server.getPort();
			
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
		System.out.println("           except me ("+host_adress+":"+host_port+") ...");
		
		for(int i=1;i<=254;i++) {
			String ip = network_prefix+i;
			
			for (int port : ports) {
				if((host_adress == null) || !(ip.equals(host_adress) && (port == host_port))) {
					connectTo(new InetSocketAddress(ip, port));
				}
			}
		}
	}
	
	
	public void startServer() throws NiaksException {
		server = new NiakworkServer(this, ports);
	}
	
	public void stopServer() {
		if(server != null) {
			server.close();
			server = null;
		}
		
		for (NiakworkHostSocket host : hosts) {
			host.close();
		}
		for (NiakworkPlayerSocket client : clients) {
			client.close();
		}
		
		hosts = new ArrayList<NiakworkHostSocket>();
		clients = new ArrayList<NiakworkPlayerSocket>();
	}
	
	
	
	public void close() {
		stopServer();
	}
	
	public NiakworkHostSocket[] getHostsFound() {
		NiakworkHostSocket[] ret = new NiakworkHostSocket[hosts.size()];
		hosts.toArray(ret);
		return ret;
	}
	

	
	
	public Niaks getNiaks() {
		return niaks;
	}
	
	

	public static BufferedWriter getBW(Socket socket) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public static BufferedReader getBR(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	
}
