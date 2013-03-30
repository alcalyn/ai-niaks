package model;

public class Strategies {
	
	/*
	 * L'ordi joue le coup qui reduit le plus
	 * l'espace de chacun de ses pions � l'arrivee
	 * 
	 * Avantages :
	 * 		- simple � coder
	 * 		- L'ordi joue deja convenablement
	 * 
	 * Inconvenients :
	 * 		- Il risque de laisser des pions � l'arriere
	 * 			si il ne trouve pas d'occasion pour l'avancer.
	 *			Et si on connait sa strat�gie, on fait en sorte
	 *			de ne pas aider ses pions arri�re.
	 */
	public static int simpleStrategie(Plateau plateau, Joueur joueur) {
		return - plateau.evalJoueur(joueur);
	}
	
	
	/*
	 * Pareil que pour la simple strategie,
	 * sauf que la distance est elevee au carree
	 * afin de penaliser les pions les plus eloignes.
	 * 
	 * Avantages :
	 * 		- pas plus complique
	 * 
	 * Inconvenients :
	 * 		- On peut encore le bloquer
	 */
	public static int backFirstStrategie(Plateau plateau, Joueur joueur) {
		int sum = 0;
		
		for (Pion p : plateau.getPions(joueur)) {
			int evalPion = plateau.evalPion(p);
			sum += evalPion * evalPion;
		}
		
		return - sum;
	}
	
}
