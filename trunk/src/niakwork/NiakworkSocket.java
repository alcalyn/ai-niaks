package niakwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class NiakworkSocket {
	
	
	protected Niakwork niakwork;
	private Socket socket;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private Thread thread;
	
	
	public NiakworkSocket(Niakwork niakwork, Socket socket) {
		super();
		this.niakwork = niakwork;
		this.socket = socket;
		
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			thread = new Thread(new ReaderThread());
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void send(NiakworkQuery s) {
		try {
			oos.writeObject(s);
			oos.flush();
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
	public void send(String opcode, Object o) {
		send(new NiakworkQuery(opcode, o));
	}
	public void send(String opcode, Object o, Object o2) {
		send(new NiakworkQuery(opcode, o, o2));
	}
	
	
	public Socket getSocket() {
		return socket;
	}
	
	
	
	
	
	public void close() {
		try {
			thread.interrupt();
			socket.close();
			ois.close();
			oos.close();
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
				NiakworkQuery query = null;
				
				try {
					query = (NiakworkQuery) ois.readObject();
				} catch (IOException e) {
					e.printStackTrace();
					close();
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				if(query != null) {
					System.out.println("Requete recu : "+query.toString());
					received(query);
				}
			}
		}
		
	}
	
	
}
