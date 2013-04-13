package strategies;

import model.Joueur;
import model.Plateau;
import model.Strategies;
import model.Strategy;

public class SimpleStrategy implements Strategy {

	
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
	
	
	@Override
	public double calc(Plateau plateau, Joueur max) {
		int eval_max = 0;
		
		if(plateau.hasWon(max)) {
			eval_max = Strategies.MAX_SCORE;
		} else {
			eval_max = - plateau.evalJoueur(max);
		}
		
		return eval_max;
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		int eval_max = 0;
		int eval_min = 0;
		
		if(plateau.hasWon(max) && !plateau.getPartie().getPlateau().hasWon(max)) {
			return Strategies.MAX_SCORE;
		} else {
			eval_max = - plateau.evalJoueur(max);
		}
		
		if(plateau.hasWon(min) && !plateau.getPartie().getPlateau().hasWon(min)) {
			return Strategies.MIN_SCORE;
		} else {
			eval_min = - plateau.evalJoueur(min);
		}
		
		return eval_max - eval_min;
	}
	
}
