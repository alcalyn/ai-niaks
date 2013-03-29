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
		Coup coup = getPartie().autoPlay().getLastCoup();
		
		Pion p = getPartie().getPlateau().getCase(coup.getCaseDepart());
		Coup ret = new Coup(p, coup.getCaseArrivee());
		return ret;
	}


	@Override
	public boolean playsInstantly() {
		return true;
	}

}
