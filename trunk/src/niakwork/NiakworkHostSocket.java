package niakwork;

import java.net.Socket;

public class NiakworkHostSocket extends NiakworkSocket {

	public NiakworkHostSocket(Niakwork niakwork, Socket socket) {
		super(niakwork, socket);
	}
	
	
	public void queryJoin() {
		send(NiakworkQuery.I_WANT_TO_JOIN, niakwork.getNiaks().getProfil());
	}


	@Override
	public void received(NiakworkQuery s) {
		System.out.println("NHostsocket recoit : "+s);
	}
	
	
	

}
