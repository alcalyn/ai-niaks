package strategies;

import model.Joueur;
import model.Pion;
import model.Plateau;
import model.Strategy;

public class PionIsoleStrategy implements Strategy {

	@Override
	public double calc(Plateau plateau, Joueur max) {
		Pion [] pions = plateau.getPions(max);
		
		int distance_max = -10;
		
		for (Pion a : pions) {
			int distance_min = 100000;
			
			for (Pion b : pions) {
				if(a != b) {
					distance_min = Math.min(a.getCoords().distance(b.getCoords()), distance_min);
				}
			}
			
			distance_max = Math.max(distance_max, distance_min);
		}
		
		return - distance_max;
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		return calc(plateau, max) - calc(plateau, min);
	}

}
