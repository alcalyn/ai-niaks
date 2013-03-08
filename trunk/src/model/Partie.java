package model;

public class Partie extends Model {
	
	
	private Plateau plateau;
	private int taille_plateau;
	private Joueur[] joueurs;
	
	
	
	public Partie(Joueur[] joueurs, int taille_plateau) {
		this.joueurs = joueurs;
		this.taille_plateau = taille_plateau;
		this.plateau = new Plateau(taille_plateau, joueurs);
		notifyPions(plateau.getPions());
	}



	public int getTaillePlateau() {
		return taille_plateau;
	}
	
	public int getNbJoueur() {
		return joueurs.length;
	}
	
	
	public int getJoueur() {
		return plateau.getJoueur();
	}
	
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	
	public Joueur getJoueur(int i) {
		return joueurs[i];
	}
	
	public int nextJoueur() {
		plateau.nextJoueur();
		notifyCurrentPlayer(joueurs[plateau.getJoueur()]);
		return plateau.getJoueur();
	}



	public Plateau getPlateau() {
		return plateau;
	}
	
}
