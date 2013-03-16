package niakwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public abstract class NiakworkSocket {
	
	
	
	
	
	
	protected Niakwork niakwork;
	private Socket socket;
	
	private BufferedReader br;
	private BufferedWriter bw;
	
	private Thread thread;
	
	
	public NiakworkSocket(Niakwork niakwork, Socket socket) {
		super();
		this.niakwork = niakwork;
		this.socket = socket;
		
		try {
			br = Niakwork.getBR(socket);
			bw = Niakwork.getBW(socket);
			
			thread = new Thread(new ReaderThread());
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void send(NiakworkQuery s) {
		try {
			bw.write(s.toString());
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String opcode) {
		send(new NiakworkQuery(opcode));
	}
	public void send(String opcode, String arg1) {
		send(new NiakworkQuery(opcode, arg1));
	}
	
	
	public Socket getSocket() {
		return socket;
	}
	
	
	
	
	
	public void close() {
		try {
			thread.interrupt();
			socket.close();
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getFullAdress() {
		return socket.getInetAddress().getHostAddress()+":"+socket.getPort();
	}
	
	
	public abstract void received(NiakworkQuery nquery);
	
	
	
	private class ReaderThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				String s = null;
				
				try {
					s = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					close();
					break;
				}
				
				if(s != null) {
					System.out.println("Requete recu : "+s.toString());
					received(new NiakworkQuery(s));
				}
			}
		}
		
	}
	
	
}
