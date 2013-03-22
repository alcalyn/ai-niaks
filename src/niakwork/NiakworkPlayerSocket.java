
package niakwork;

import java.net.Socket;

import model.Coords;
import model.Joueur;

public class NiakworkPlayerSocket extends NiakworkSocket {
	
	
	
	public NiakworkPlayerSocket(Niakwork niakwork, Socket socket) {
		super(niakwork, socket);
	}
	
	
	
	public void querySendPseudo() {
		send(NiakworkQuery.MY_NAME_IS, niakwork.getNiaks().getProfil());
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
	

	public void queryUpdateCurrentPlayer(String pseudo) {
		System.out.println("Envoi update current player");
		send(NiakworkQuery.UPDATE_CURRENT_PLAYER, pseudo);
	}
	
	public void queryUpdatePions(Coords[][] coords) {
		System.out.println("Envoi update pions coords");
		send(NiakworkQuery.UPDATE_PIONS, coords);
	}
	
	public void queryUpdateJoueurWon(Joueur joueur) {
		send(NiakworkQuery.UPDATE_JOUEUR_WON, joueur);
	}
	

	@Override
	public void received(NiakworkQuery nquery) {
		System.out.println("NPlayersocket recoit : "+nquery.opcode);
		
		if(nquery.is(NiakworkQuery.I_WANT_TO_JOIN)) {
			niakwork.getNiaks().niakworkClientWantJoin(this, nquery.arg(0).toString());
		}
		
		
		if(nquery.is(NiakworkQuery.UPDATE_CURRENT_PLAYER)) {
			System.out.println("reception update current player");
			niakwork.getNiaks().niakworkUpdateCurrentPlayer((String) nquery.arg(0));
		}
		
		if(nquery.is(NiakworkQuery.UPDATE_PIONS)) {
			System.out.println("reception update pions coords");
			niakwork.getNiaks().niakworkUpdatePions((Coords[][]) nquery.arg(0));
		}
		
		if(nquery.is(NiakworkQuery.UPDATE_JOUEUR_WON)) {
			System.out.println("reception update pions coords");
			niakwork.getNiaks().niakworkUpdateWinner((Joueur) nquery.arg(0));
		}
	}

}
