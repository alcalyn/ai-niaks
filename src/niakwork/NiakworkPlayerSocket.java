
package niakwork;

import java.net.Socket;

import model.Joueur;

public class NiakworkPlayerSocket extends NiakworkSocket {
	
	
	
	public NiakworkPlayerSocket(Niakwork niakwork, Socket socket) {
		super(niakwork, socket);
	}
	
	
	public void queryAcceptJoin() {
		send(NiakworkQuery.OK_COME_ON);
		queryUpdatePlayers();
	}
	
	public void queryDenyJoin() {
		send(NiakworkQuery.DENY_JOIN);
	}
	
	public void queryGameAlreadyStarted() {
		send(NiakworkQuery.DENY_JOIN_GAME_STARTED);
	}
	
	public void queryUpdatePlayers() {
		String [] pseudos = new String[niakwork.getNiaks().getPartiePreparator().getNbJoueur()];
		
		int i = 0;
		for (Joueur j : niakwork.getNiaks().getPartiePreparator().getJoueurs()) {
			pseudos[i++] = j.getPseudo();
		}
		
		send(
				NiakworkQuery.UPDATE_PARTIE_PREPARATOR,
				pseudos,
				new Integer(niakwork.getNiaks().getPartiePreparator().getPlateauSize())
		);
	}
	
	public void queryStartGame() {
		queryUpdatePlayers();
		send(NiakworkQuery.GAME_STARTED);
	}
	
	

	@Override
	public void received(NiakworkQuery nquery) {
		System.out.println("NPlayersocket recoit : "+nquery.opcode);
		
		if(nquery.is(NiakworkQuery.I_WANT_TO_JOIN)) {
			niakwork.getNiaks().niakworkClientWantJoin(this, nquery.arg(0).toString());
		}
	}

}
