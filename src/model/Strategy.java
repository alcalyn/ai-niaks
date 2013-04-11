package model;

public interface Strategy {
	
	/**
	 * Dans le cas de 1 joueur
	 * @param plateau
	 * @param max
	 * @return
	 */
	public double calc(Plateau plateau, Joueur max);
	
	/**
	 * Dans le cas de 2 joueurs
	 * @param plateau
	 * @param max
	 * @param min
	 * @return
	 */
	public double calc(Plateau plateau, Joueur max, Joueur min);
	
}
