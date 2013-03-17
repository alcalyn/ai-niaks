package niakwork;

import java.net.Socket;


import exceptions.PartieNotReadyToStartNiaksException;

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
		
		if(nquery.is(NiakworkQuery.UPDATE_PARTIE_PREPARATOR)) {
			niakwork.getNiaks().niakworkUpdatePartiePreparator(this, (String[]) nquery.arg(0), (Integer) nquery.arg(1));
		}
		
		if(nquery.is(NiakworkQuery.GAME_STARTED)) {
			try {
				niakwork.getNiaks().startPartie();
			} catch (PartieNotReadyToStartNiaksException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	

}
