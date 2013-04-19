package niakwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class NiakworkAuth extends Thread {
	
	
	private Niakwork niakwork;
	private Socket socket;
	
	
	public NiakworkAuth(Niakwork niakwork, Socket socket) {
		super("Thread-NiakworkAuth");
		this.niakwork = niakwork;
		this.socket = socket;
		start();
	}
	
	
	
	@Override
	public void run() {
		try {
			BufferedReader br = Niakwork.getBR(socket);
			BufferedWriter bw = Niakwork.getBW(socket);
			
			System.out.println("ServerAuth > waiting for protocol header...");
			
			String read = null;
			
			while(true) {
				read = br.readLine();
				
				if(read != null) {
					if(read.equalsIgnoreCase(Niakwork.niawkwork_version)) {
						System.out.println("ServerAuth > protocol header received");
						System.out.println("ServerAuth > sending OK to client");
						bw.write(Niakwork.niawkwork_version+" OK");
						bw.newLine();
						bw.flush();
						System.out.println("ServerAuth > notify niakwork and break");
						niakwork.notifyAuthentifiedClient(socket);
						break;
					} else {
						System.out.println("ServerAuth > bad protocol header received, abort");
						br.close();
						bw.close();
						break;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
