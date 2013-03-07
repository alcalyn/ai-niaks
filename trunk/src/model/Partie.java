package model;

public class Partie extends Model {
	
	
	private Plateau plateau;
	private int taille_plateau;
	private int nb_joueur;
	
	
	
	public Partie(int nb_joueur, int taille_plateau) {
		this.nb_joueur = nb_joueur;
		this.taille_plateau = taille_plateau;
		this.plateau = new Plateau(this);
		notifyPions(plateau.getPions());
	}



	public int getTaillePlateau() {
		return taille_plateau;
	}
	
	public int getNbJoueur() {
		return nb_joueur;
	}
	
	
	public char getJoueur() {
		return plateau.getJoueur();
	}
	
	public char nextJoueur() {
		plateau.nextJoueur();
		notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueur();
	}



	public Plateau getPlateau() {
		return plateau;
	}
	
}
