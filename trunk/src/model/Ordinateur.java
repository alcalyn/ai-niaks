package model;

import exceptions.IllegalMoveNiaksException;

public class Ordinateur extends Joueur {
	
	private static final long serialVersionUID = -1624054167594296013L;
	
	

	private static int cpu_count = 0;
	
	private Coup coup_calcule = null;
	
	private Strategy strategy;
	
	
	public Ordinateur(Strategy strategy) {
		super(getOrdiPseudo());
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
				Plateau plateau = null;
				if(getPartie() == null || (plateau = getPartie().autoPlay()) == null) {
					return;
				}
				
				Coup coup = plateau.getLastCoup();
				
				if(coup == null) return;
				
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
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void setStrategy(String strategy) {
		this.strategy = Strategies.getStrategyByDifficult(strategy);
	}




}
