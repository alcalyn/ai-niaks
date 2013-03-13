package niakwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NiakworkAuth extends Thread {
	
	
	private Niakwork niakwork;
	private Socket socket;
	
	
	public NiakworkAuth(Niakwork niakwork, Socket socket) {
		this.niakwork = niakwork;
		this.socket = socket;
	}
	
	
	
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String read;
			boolean ok = false;
			
			while(!ok && ((read = br.readLine()) != null)) {
				if(read.equalsIgnoreCase(Niakwork.niawkork_version)) {
					bw.write(Niakwork.niawkork_version+" OK");
					bw.flush();
					niakwork.notifyAuthentifiedClient(socket);
					ok = true;
				} else {
					break;
				}
			}
			
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
