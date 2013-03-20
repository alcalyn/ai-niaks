package model;

import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayerSocket;

public interface Observer {
	
	
	public void updateProfil(String pseudo);
	
	public void updateEtat(int etat_partie);
	
	public void updateTaillePlateau(int taille);
	
	public void updateJoueurs(Joueur joueurs[]);
	
	public void updatePions(Pion[][] pions);
	
	public void updateCurrentPlayer(Joueur joueur);
	
	public void updateJoueurWon(Joueur joueur);
	
	public void updateNiakwork(boolean isEnabled);

	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo);

	public void updateNiakworkServerFound(NiakworkHostSocket nssocket, String pseudo);

	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket, String reason);

	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket);

	public void updateGameFinished();
	
	
}
