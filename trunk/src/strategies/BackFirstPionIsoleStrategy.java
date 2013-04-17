package strategies;

import model.Joueur;
import model.Plateau;
import model.Strategy;

public class BackFirstPionIsoleStrategy implements Strategy {
	
	
	private Strategy back_first, pion_isole;
	
	public BackFirstPionIsoleStrategy() {
		back_first = new BackFirstStrategy();
		pion_isole = new PionIsoleStrategy();
	}
	
	@Override
	public double calc(Plateau plateau, Joueur max) {
		return back_first.calc(plateau, max) + 10 * pion_isole.calc(plateau, max);
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		return back_first.calc(plateau, max, min) + 10 * pion_isole.calc(plateau, max, min);
	}

}
