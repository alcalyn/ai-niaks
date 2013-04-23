package model;

import minimax.Minimax;
import minimax.MinimaxElagator;
import minimax.MinimaxNode;
import exceptions.IllegalMoveNiaksException;

public class Partie {
	
	private static final int [] minimax_depth = new int []
			{10, 3, 1, 0, 0, 0};
	
	
	private Niaks niaks;

	private Plateau plateau;
	private int taille_plateau;
	private Integer min_eval = null;
	private Joueur[] joueurs;
	private boolean coup_longs = false;
	private boolean multiple_coup_longs = false;
	private Minimax minimax;
	private boolean isFinished = false;
	
	
	
	public Partie(Niaks niaks, Joueur[] joueurs, final int taille_plateau) {
		this.niaks = niaks;
		this.joueurs = joueurs;

		for (Joueur joueur : joueurs) {
			joueur.attachPartie(this);
		}

		this.taille_plateau = taille_plateau;
		this.plateau = new Plateau(taille_plateau, joueurs);
		this.minimax = new Minimax();
		this.minimax.setDefaultElagator(new MinimaxElagator() {
			
			@Override
			public boolean horizon(MinimaxNode node, int depth) {
				boolean in_depth_range = depth <= minimax_depth[taille_plateau - 1];
				boolean is_finished = Math.abs(node.eval()) == Strategies.MAX_SCORE;
				
				return in_depth_range && !is_finished;
			}
			
			@Override
			public boolean elage(MinimaxNode node, int depth) {
				if(node.player()) {
					return node.eval() >= Strategies.MAX_SCORE;
				} else {
					return node.eval() <= Strategies.MIN_SCORE;
				}
			}

		});
		
		niaks.notifyPions(plateau.getPions(), null);

		start();
	}
	
	
	public Plateau autoPlay() {
		plateau.reinitMinimaxNode();
		Plateau next = (Plateau) minimax.getNext(plateau, minimax.getDefaultElagator());
		return next;
	}
	
	


	/**
	 * 
	 * @param coup ï¿½ faire jouer tout de suite
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public void jouerCoup(Coup coup) throws IllegalMoveNiaksException {
		coup = plateau.coupValide(coup);

		plateau.movePion(coup.getPion(), coup.getCaseArrivee());
		niaks.notifyPions(plateau.getPions(), coup);
		nextJoueur();


		if(!niaks.isHost()) {
			Pion [][] pions = plateau.getPions();
			Coords [][] coords = new Coords[pions.length][pions[0].length];
			for(int i=0;i<pions.length;i++) {
				for(int j=0;j<pions[i].length;j++) {
					coords[i][j] = pions[i][j].getCoords();
				}
			}
			
			niaks.getHost().queryUpdatePions(coords);
			niaks.getHost().queryUpdateCurrentPlayer(plateau.getJoueur().getPseudo());
		}
	}


	/**
	 * 
	 * @param joueur qui vient de jouer sur un autre thread (humain sur l'UI ou joueur distant)
	 * @throws IllegalMoveNiaksException
	 */
	public void notifyCoupPlayed(Joueur joueur) throws IllegalMoveNiaksException {
		if(joueur == plateau.getJoueur()) {
			Coup coup = plateau.getJoueur().jouerCoup();

			if(coup != null) {
				jouerCoup(coup);
				start();
			} else {
				System.out.println("Partie :: Erreur, coup notified but null");
			}
		}
	}



	/**
	 * Fait jouer tant que le joueur joue directement (ordi)
	 * et s'arrete quand il doit attendre un coup (humain sur UI, joueur distant)
	 */
	public void start() {
		while(plateau.getJoueur().playsInstantly()) {
			Coup coup = plateau.getJoueur().jouerCoup();

			if(coup == null) {
				System.out.println("Partie : Erreur, coup instantané null");
			}

			try {
				jouerCoup(coup);
			} catch(IllegalMoveNiaksException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}


	public boolean hasWon(Joueur joueur) {
		return plateau.hasWon(joueur);
	}
	
	public boolean isFinished() {
		if(!isFinished) {
			if(plateau.isFinished()) {
				isFinished = true;
				niaks.gameFinished();
			}
		}
		
		return isFinished;
	}

	public int getTaillePlateau() {
		return taille_plateau;
	}

	public int getNbJoueur() {
		return joueurs.length;
	}

	public Joueur getJoueur() {
		return plateau.getJoueur();
	}

	public int getJoueurIndex() {
		return plateau.getJoueurIndex();
	}

	public Joueur[] getJoueurs() {
		return joueurs;
	}

	public Joueur getJoueur(int i) {
		return joueurs[i];
	}

	public int nextJoueur() {
		if(!getJoueur().hasWon()) {
			if(hasWon(getJoueur())) {
				getJoueur().setWon(true);
				niaks.notifyJoueurWon(getJoueur());
				isFinished();
			}
		}

		do {
			plateau.nextJoueur();
		} while(getJoueur().hasWon() && !plateau.isFinished());

		niaks.notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueurIndex();
	}

	public int setJoueur(Joueur joueur) {
		if(!getJoueur().hasWon()) {
			if(hasWon(getJoueur())) {
				getJoueur().setWon(true);
				niaks.notifyJoueurWon(getJoueur());
				isFinished();
			}
		}

		plateau.setJoueur(joueur);
		niaks.notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueurIndex();
	}

	public Niaks getNiaks() {
		return niaks;
	}

	public Plateau getPlateau() {
		return plateau;
	}


	public boolean isCoupLongs() {
		return coup_longs;
	}


	public boolean isMultipleCoupLongs() {
		return multiple_coup_longs;
	}


	public void setCoupLongs(boolean coup_longs) {
		this.coup_longs = coup_longs;
	}


	public void setMultipleCoupLongs(boolean multiple_coup_longs) {
		this.multiple_coup_longs = multiple_coup_longs;
	}


	public Minimax getMinimax() {
		return minimax;
	}


	public void setMinimax(Minimax minimax) {
		this.minimax = minimax;
	}
	
	public int getMinimumEval() {
		if(min_eval == null) {
			int sum = 0;
			
			for(int i=2;i<=taille_plateau;i++) {
				sum += i * (i-1);
			}
			
			min_eval = new Integer(sum);
		}
		
		return min_eval.intValue();
	}

	


}
