package model;

public class Strategies {
	
	public static int simpleStrategie(Plateau plateau, Joueur joueur) {
		return - plateau.evalJoueur(joueur);
	}
	
}
