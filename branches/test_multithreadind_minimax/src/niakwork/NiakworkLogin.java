package niakwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NiakworkLogin extends Thread {
	
	
	private Niakwork niakwork;
	private InetSocketAddress endpoint;
	
	
	public NiakworkLogin(Niakwork niakwork, InetSocketAddress endpoint) {
		super("Thread-NiakworkLogin");
		this.niakwork = niakwork;
		this.endpoint = endpoint;
		start();
	}
	
	
	public void run() {
		try {
			Socket socket = new Socket();
			socket.connect(endpoint, Niakwork.login_timeout);
			
			System.out.println("Client > Server potentiel found at "+endpoint.getAddress());
			
			BufferedWriter bw = Niakwork.getBW(socket);
			BufferedReader br = Niakwork.getBR(socket);
			
			bw.write(Niakwork.niawkwork_version);
			bw.newLine();
			bw.flush();
			
			System.out.println("Client > protocol header sent... waiting response...");
			
			String read;
			
			while(true) {
				read = br.readLine();
				System.out.println("Client > read : "+read);
				
				if(read != null) {
					if(read.equalsIgnoreCase(Niakwork.niawkwork_version+" OK")) {
						System.out.println("Client > success response received");
						niakwork.notifyAuthentifiedServer(socket);
						break;
					} else {
						System.out.println("Client > bad response received, abort");
						bw.close();
						br.close();
						break;
					}
				}
			}
		
		} catch (NoRouteToHostException e) {
			//System.out.println("Pas de route ici : "+endpoint.getAddress());
			//e.printStackTrace();
		} catch (SocketTimeoutException e) {
			//System.out.println("Rien ici : "+endpoint.getAddress()+":"+endpoint.getPort());
			//e.printStackTrace();
		} catch (ConnectException e) {
			//System.out.println("Connection refusee ici : "+endpoint.getAddress());
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
