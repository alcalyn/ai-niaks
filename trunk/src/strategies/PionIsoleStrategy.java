package strategies;

import model.Joueur;
import model.Pion;
import model.Plateau;
import model.Strategy;


/**
 * 
 * 		PionIsoleStrategy
 * 
 * Renvoi simplement la somme des distances
 * des pions isolés au reste du groupe union ceux des adversaires.
 * 
 * Utilisée seule, les pions resteront groupés, ou groupés avec l'adversaire,
 * mais ne se déplacera pas vers son but.
 *
 */
public class PionIsoleStrategy implements Strategy {
	
	
	@Override
	public double calc(Plateau plateau, Joueur max) {
		return - distancesSum(plateau.getPions(max), 2);
	}

	@Override
	public double calc(Plateau plateau, Joueur max, Joueur min) {
		Pion [] pions_max = plateau.getPions(max);
		Pion [] pions_min = plateau.getPions(min);
		
		int dist_max = distancesSum(pions_max, pions_min, 2);
		int dist_min = distancesSum(pions_min, pions_max, 2);
		
		return - (dist_max - dist_min);
	}
	
	
	/**
	 * 
	 * @param pions
	 * @return la somme des ecart des pions isolés au reste du groupe "pions"
	 */
	public int distancesSum(Pion [] pions, double pow) {
		return distancesSum(pions, new Pion[0], pow);
	}
	
	
	/**
	 * 
	 * @param pions_from
	 * @param pions_to
	 * @return la somme des ecart des pions isolés au reste du groupe "pions_from union pions_to"
	 */
	public int distancesSum(Pion [] pions_from, Pion [] pions_to, double pow) {
		int dist_min, dist_sum, distance;
		
		dist_sum = 0;
		
		for(int i=0;i<pions_from.length;i++) {
			Pion a = pions_from[i];
			dist_min = 1000;
			
			for(int j=0;j<pions_from.length;j++) {
				Pion b = pions_from[j];
				
				if(a != b) {
					distance = a.getCoords().distance(b.getCoords());
					if(distance < dist_min) dist_min = distance;
					if(dist_min <= 1) break;
				}
			}
			
			if(dist_min > 1) for(int j=0;j<pions_to.length;j++) {
				Pion b = pions_to[j];
				
				distance = a.getCoords().distance(b.getCoords());
				if(distance < dist_min) dist_min = distance;
				if(dist_min <= 1) break;
			}
			
			dist_min -= 1;
			if(dist_min > 0) dist_sum += Math.pow(dist_min, pow);
		}
		
		return dist_sum;
	}

}
