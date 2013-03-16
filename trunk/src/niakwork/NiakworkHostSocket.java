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
	public void received(NiakworkQuery nquery) {
		System.out.println("NHostsocket recoit : "+nquery);
		
		if(nquery.is(NiakworkQuery.OK_COME_ON)) {
			niakwork.getNiaks().niakworkHostJoined(this);
		}
		if(nquery.is(NiakworkQuery.DENY_JOIN)) {
			niakwork.getNiaks().niakworkHostDenied(this, "L'hôte a refusé");
		}
		if(nquery.is(NiakworkQuery.DENY_JOIN_GAME_STARTED)) {
			niakwork.getNiaks().niakworkHostDenied(this, "La partie que vous avez essayé de joindre est déjà commencée");
		}
	}
	
	
	

}
