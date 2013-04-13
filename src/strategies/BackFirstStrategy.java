package strategies;

import model.Joueur;
import model.Pion;
import model.Plateau;
import model.Strategies;
import model.Strategy;

public class BackFirstStrategy implements Strategy {

	
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
	
	
	@Override
	public double calc(Plateau plateau, Joueur max) {
		int eval_max = 0;
		
		if(plateau.hasWon(max)) {
			eval_max = Strategies.MAX_SCORE;
		} else {
			for (Pion p : plateau.getPions(max)) {
				int evalPion = plateau.evalPion(p);
				eval_max -= evalPion * evalPion;
			}
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
			for (Pion p : plateau.getPions(max)) {
				int evalPion = plateau.evalPion(p);
				eval_max -= evalPion * evalPion;
			}
		}
		
		if(plateau.hasWon(min) && !plateau.getPartie().getPlateau().hasWon(min)) {
			return Strategies.MIN_SCORE;
		} else {
			for (Pion p : plateau.getPions(min)) {
				int evalPion = plateau.evalPion(p);
				eval_min -= evalPion * evalPion;
			}
		}
		
		return eval_max - eval_min;
	}

}
