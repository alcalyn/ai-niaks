package strategies;

import model.Joueur;
import model.Plateau;
import model.Strategy;


/**
 * 
 * 		BackFirstPionIsoleStrategy
 * 
 * 
 * Union de back first et pion isol�, avec des coefficient constants.
 * L'ordi avancera en privil�giant les pions arri�re et en �vitant
 * d'isoler les pions.
 * 
 * Avantages :
 * 		- dans les grands plateau (taille >= 4), l'ordi
 * 			avancera mieux, surtout � la fin ou il n'aura
 * 			pas de pions isol�.
 * 
 * Inconv�nients :
 * 		- Dans les petits plateau, cela n'apporte pas beaucoup
 * 			d'avantage car �tant petit, il est rare qu'un pion
 * 			s'isole. Cela ne fera alors que ralentir l'ordi.
 */
public class BackFirstPionIsoleStrategy implements Strategy {
	
	
	private Strategy
		back_first,
		pion_isole;
	
	private static final int
		coef_back_first = 1,
		coef_pion_isole = 1;
	
	
	
	public BackFirstPionIsoleStrategy() {
		back_first = new BackFirstStrategy();
		pion_isole = new PionIsoleStrategy();
	}
	
	
	
	@Override
	public double calc(Plateau plateau, Joueur max) {
		return
			coef_back_first * back_first.calc(plateau, max) +
			coef_pion_isole * pion_isole.calc(plateau, max);
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		return
			coef_back_first * back_first.calc(plateau, max, min) +
			coef_pion_isole * pion_isole.calc(plateau, max, min);
	}

}
