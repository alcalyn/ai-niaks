package niakwork;

import java.net.Socket;

import model.Coords;
import model.Coup;
import model.Joueur;
import model.Observer;
import model.Pion;


import exceptions.PartieNotReadyToStartNiaksException;

public class NiakworkHostSocket extends NiakworkSocket implements Observer {

	public NiakworkHostSocket(Niakwork niakwork, Socket socket) {
		super(niakwork, socket);
	}
	
	
	public void queryJoin() {
		send(NiakworkQuery.I_WANT_TO_JOIN, niakwork.getNiaks().getProfil());
	}
	
	public void queryUpdateCurrentPlayer(String pseudo) {
		System.out.println("Envoi update current player");
		send(NiakworkQuery.UPDATE_CURRENT_PLAYER, pseudo);
	}
	
	public void querySocketClose() {
		send(NiakworkQuery.SOCKET_CLOSE);
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
		
		if(nquery.is(NiakworkQuery.UPDATE_CURRENT_PLAYER)) {
			System.out.println("reception update current player");
			niakwork.getNiaks().niakworkUpdateCurrentPlayer((String) nquery.arg(0));
		}
		
		if(nquery.is(NiakworkQuery.UPDATE_PIONS)) {
			System.out.println("reception update pions coords");
			niakwork.getNiaks().niakworkUpdatePions((Coords[][]) nquery.arg(0));
		}
		
		if(nquery.is(NiakworkQuery.UPDATE_JOUEUR_WON)) {
			System.out.println("reception update joueur won");
			niakwork.getNiaks().niakworkUpdateWinner((Joueur) nquery.arg(0));
		}
		
	}
	

	
	
	
	
	@Override
	public void updateProfil(String pseudo) {
	}

	@Override
	public void updateEtat(int etat_partie) {
	}

	@Override
	public void updateTaillePlateau(int taille) {
	}

	@Override
	public void updateJoueurs(Joueur[] joueurs) {
	}
	
	

	@Override
	public void updatePions(Pion[][] pions, Coup coup) {
		Coords [][] coords = new Coords[pions.length][pions[0].length];
		
		for(int i=0;i<pions.length;i++) {
			for(int j=0;j<pions[i].length;j++) {
				coords[i][j] = pions[i][j].getCoords();
			}
		}
		
		queryUpdatePions(coords);
	}

	
	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		queryUpdateCurrentPlayer(joueur.getPseudo());
	}

	
	
	
	@Override
	public void updateNiakwork(boolean isEnabled) {
	}

	@Override
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket,
			String pseudo) {
	}

	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket,
			String pseudo) {
	}

	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket,
			String reason) {
	}

	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
	}


	@Override
	public void updateJoueurWon(Joueur joueur) {
		queryUpdateJoueurWon(joueur);
	}


	@Override
	public void updateGameFinished() {
		// TODO Auto-generated method stub
		
	}
	

}

