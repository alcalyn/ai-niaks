package model;

public class Strategies {
	
	
	public static final int
		MAX_SCORE =  100000,
		MIN_SCORE = -100000;
	
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
		int eval_max = 0;
		int eval_min = 0;
		
		if(plateau.hasWon(max)) {
			eval_max = MAX_SCORE - 100 * plateau.getTour();
			return eval_max;
		} else {
			eval_max = - plateau.evalJoueur(max);
		}
		
		if(plateau.hasWon(min)) {
			eval_min = MAX_SCORE - 100 * plateau.getTour();
			return - eval_min;
		} else {
			eval_min = - plateau.evalJoueur(min);
		}
		
		return eval_max - eval_min;
	}
	
	public static int simpleStrategie(Plateau plateau, Joueur max) {
		int eval_max = 0;
		
		if(plateau.hasWon(max)) {
			eval_max = MAX_SCORE;
		} else {
			eval_max = - plateau.evalJoueur(max);
		}
		
		return eval_max;
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
		
		if(plateau.hasWon(max)) {
			eval_max = MAX_SCORE;
			return eval_max;
		} else {
			for (Pion p : plateau.getPions(max)) {
				int evalPion = plateau.evalPion(p);
				eval_max -= evalPion * evalPion;
			}
		}
		
		if(plateau.hasWon(min)) {
			eval_min = MAX_SCORE;
			return - eval_min;
		} else {
			for (Pion p : plateau.getPions(min)) {
				int evalPion = plateau.evalPion(p);
				eval_min -= evalPion * evalPion;
			}
		}
		
		return eval_max - eval_min;
	}
	
	public static int backFirstStrategie(Plateau plateau, Joueur max) {
		int eval_max = 0;
		
		if(plateau.hasWon(max)) {
			eval_max = MAX_SCORE;
		} else {
			for (Pion p : plateau.getPions(max)) {
				int evalPion = plateau.evalPion(p);
				eval_max -= evalPion * evalPion;
			}
		}
		
		return eval_max;
	}
	
}
