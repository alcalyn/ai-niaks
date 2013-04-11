package model;

import exceptions.IllegalMoveNiaksException;

public class Ordinateur extends Joueur {
	
	private static int cpu_count = 0;
	
	private Coup coup_calcule = null;
	
	private double difficulte;
	private Strategy strategy;
	
	
	public Ordinateur(double difficulte, Strategy strategy) {
		super(getOrdiPseudo());
		this.difficulte = difficulte;
		this.strategy = strategy;
	}
	
	
	private static String getOrdiPseudo() {
		return "Ordinateur "+(++cpu_count);
	}
	

	@Override
	public Coup jouerCoup() {
		return coup_calcule;
	}


	@Override
	public boolean playsInstantly() {
		final Joueur current_player = this;
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Coup coup = getPartie().autoPlay().getLastCoup();
				
				Pion p = getPartie().getPlateau().getCase(coup.getCaseDepart());
				coup_calcule = new Coup(p, coup.getCaseArrivee());
				
				try {
					getPartie().notifyCoupPlayed(current_player);
				} catch (IllegalMoveNiaksException e) {
					System.out.println("L'ordi a joue un coup illegal");
					e.printStackTrace();
				}
			}
		});
		
		t.start();
		
		return false;
	}
	
	
	public Strategy getStrategy() {
		return strategy;
	}




}
