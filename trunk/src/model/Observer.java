package model;

import niakwork.NiakworkPlayerSocket;

public interface Observer {
	
	
	public void updateProfil(String pseudo);
	
	public void updateEtat(int etat_partie);
	
	public void updateTaillePlateau(int taille);
	
	public void updateJoueurs(Joueur joueurs[]);
	
	public void updatePions(Pion[][] pions);
	
	public void updateCurrentPlayer(Joueur joueur);
	
	public void updateNiakwork(boolean isEnabled);

	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo);
	
}
