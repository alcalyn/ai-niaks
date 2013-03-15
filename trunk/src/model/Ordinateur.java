package model;

public class Ordinateur extends Joueur {
	
	private static int cpu_count = 0;
	
	private double difficulte;
	
	
	public Ordinateur(double difficulte) {
		super(getOrdiPseudo());
		this.difficulte = difficulte;
	}
	
	
	private static String getOrdiPseudo() {
		return "Ordinateur "+(++cpu_count);
	}
	

	@Override
	public Coup jouerCoup() {
		// TODO Implementer minimax
		return null;
	}


	@Override
	public boolean playsInstantly() {
		return true;
	}

}