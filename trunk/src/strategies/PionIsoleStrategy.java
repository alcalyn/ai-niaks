package strategies;

import model.Joueur;
import model.Pion;
import model.Plateau;
import model.Strategy;

public class PionIsoleStrategy implements Strategy {

	@Override
	public double calc(Plateau plateau, Joueur max) {
		return -distanceMax(plateau.getPions(max));
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		// TODO
		return 0.0;
	}
	
	
	/**
	 * 
	 * @param pions
	 * @return l'ecart maximal entre un pion isolé et le reste du groupe
	 */
	private double distanceMax(Pion [] pions) {
		int dist_min;
		int dist_max;
		int distance;
		
		dist_max = -1000;
		
		for(int i=0;i<pions.length - 1;i++) {
			Pion a = pions[i];
			dist_min = 1000;
			
			for(int j=i+1;j<pions.length;j++) {
				Pion b = pions[j];
				
				distance = a.getCoords().distance(b.getCoords());
				dist_min = Math.min(distance, dist_min);
				if(dist_min <= 1) break;
			}
			
			dist_max = Math.max(dist_min, dist_max);
		}
		
		return dist_max;
	}
	
	
	/**
	 * 
	 * @param pions_from
	 * @param pions_to
	 * @return l'ecart maximal entre un pion "from" et le reste du groupe "from union to"
	 */
	private double distanceMax(Pion [] pions_from, Pion [] pions_to) {
		// TODO
		return 0.0;
	}

}
