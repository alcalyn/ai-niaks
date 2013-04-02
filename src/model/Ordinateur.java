package model;

import exceptions.IllegalMoveNiaksException;

public class Ordinateur extends Joueur {
	
	private static int cpu_count = 0;
	
	private Coup coup_calcule = null;
	
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
		Coup c = coup_calcule;
		coup_calcule = null;
		return c;
		
		if(coup_calcule == null) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Coup coup = getPartie().autoPlay().getLastCoup();
					
					Pion p = getPartie().getPlateau().getCase(coup.getCaseDepart());
					Coup ret = new Coup(p, coup.getCaseArrivee());
					
					getPartie().notifyCoupPlayed(this);
				}
			});
		}
		
		
	}


	@Override
	public boolean playsInstantly() {
		return false;
	}




}
