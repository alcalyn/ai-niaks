package model;

public class Strategies {
	
	/*
	 * L'ordi joue le coup qui reduit le plus
	 * l'espace de chacun de ses pions à l'arrivee
	 * 
	 * Avantages :
	 * 		- simple à coder
	 * 		- L'ordi joue deja convenablement
	 * 
	 * Inconvenients :
	 * 		- Il risque de laisser des pions à l'arriere
	 * 			si il ne trouve pas d'occasion pour l'avancer.
	 *			Et si on connait sa stratégie, on fait en sorte
	 *			de ne pas aider ses pions arrière.
	 */
	public static int simpleStrategie(Plateau plateau, Joueur max, Joueur min) {
		return - (plateau.evalJoueur(max) - plateau.evalJoueur(min));
	}
	
	public static int simpleStrategie(Plateau plateau, Joueur max) {
		return - plateau.evalJoueur(max);
	}
	
	
	/*
	 * Pareil que pour la simple strategie,
	 * sauf que la distance est elevee au carree
	 * afin de penaliser les pions les plus eloignes.
	 * 
	 * Avantages :
	 * 		- aussi simple a coder, le code ne change presque pas,
	 * 			la complexite non plus
	 * 
	 * Inconvenients :
	 * 		- Meme s'il a tendance a rapprocher ses pions arriere,
	 * 			il en laisse toujours un ou deux a un moment de la partie.
	 * 			Il suffit donc de ne pas l'aider a les remonter, et on gagne
	 * 			aisement car il ne voit pas assez en profondeur.
	 */
	public static int backFirstStrategie(Plateau plateau, Joueur max, Joueur min) {
		int eval_max = 0;
		int eval_min = 0;
		
		for (Pion p : plateau.getPions(max)) {
			int evalPion = plateau.evalPion(p);
			eval_max += evalPion * evalPion;
		}
		
		for (Pion p : plateau.getPions(min)) {
			int evalPion = plateau.evalPion(p);
			eval_min += evalPion * evalPion;
		}
		
		return - (eval_max - eval_min);
	}
	
	public static int backFirstStrategie(Plateau plateau, Joueur max) {
		int eval_max = 0;
		
		for (Pion p : plateau.getPions(max)) {
			int evalPion = plateau.evalPion(p);
			eval_max += evalPion * evalPion;
		}
		
		return - eval_max;
	}
	
}
