package niakwork;

import model.Coords;
import model.Coup;
import model.Joueur;
import model.Observer;
import model.Pion;



public class NiakworkPlayer extends Joueur implements Observer {
	
	private static final long serialVersionUID = -2703369154033979320L;
	
	
	private NiakworkPlayerSocket npsocket;
	

	public NiakworkPlayer(String pseudo, NiakworkPlayerSocket npsocket) {
		super(pseudo);
		this.npsocket = npsocket;
	}
	
	public NiakworkPlayerSocket getNiakworkPlayerSocket() {
		return npsocket;
	}
	
	
	

	

	@Override
	public Coup jouerCoup() {
		return null;
	}

	@Override
	public boolean playsInstantly() {
		return false;
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
		
		npsocket.queryUpdatePions(coords);
	}

	
	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		npsocket.queryUpdateCurrentPlayer(joueur.getPseudo());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameFinished() {
		// TODO Auto-generated method stub
		
	}

}
