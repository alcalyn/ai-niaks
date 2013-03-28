package model;

import minimax.Minimax;
import minimax.MinimaxElagator;
import minimax.MinimaxNode;
import exceptions.IllegalMoveNiaksException;

public class Partie {

	private Niaks niaks;

	private Plateau plateau;
	private int taille_plateau;
	private Joueur[] joueurs;
	private Minimax minimax;

	private boolean isFinished = false;


	public Partie(Niaks niaks, Joueur[] joueurs, int taille_plateau) {
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
				return depth == 0;
			}
			
			@Override
			public boolean elage(MinimaxNode node, int depth) {
				return false;
			}

		});
		
		niaks.notifyPions(plateau.getPions(), null);

		start();
	}
	
	
	public Plateau autoPlay() {
		return (Plateau) minimax.getNext(plateau, minimax.getDefaultElagator());
	}
	
	


	/**
	 * 
	 * @param coup ï¿½ faire jouer tout de suite
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public void jouerCoup(Coup coup) throws IllegalMoveNiaksException {
		System.out.println(getJoueur()+" joue "+coup);
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
	public void notifyCoupPlayed(CoupListener joueur) throws IllegalMoveNiaksException {
		if(joueur == plateau.getJoueur()) {
			Coup coup = plateau.getJoueur().jouerCoup();

			if(coup != null) {
				jouerCoup(coup);
				start();
			} else {
				System.out.println("Partie :: Erreur, coup notified but null");
			}
		} else {
			joueur.purge();
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
		Pion [] pions = plateau.getPions(joueur);

		for (Pion pion : pions) {
			if(plateau.getZone(pion.getCoords()) != joueur.getEndZone()) {
				return false;
			}
		}

		return true;
	}

	public void checkGameFinished() {
		for (Joueur joueur : joueurs) {
			if(!joueur.hasWon()) {
				return;
			}
		}

		isFinished = true;
		niaks.gameFinished();
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
				checkGameFinished();
			}
		}

		do {
			plateau.nextJoueur();
		} while(getJoueur().hasWon() && !isFinished);

		niaks.notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueurIndex();
	}

	public int setJoueur(Joueur joueur) {
		if(!getJoueur().hasWon()) {
			if(hasWon(getJoueur())) {
				getJoueur().setWon(true);
				niaks.notifyJoueurWon(getJoueur());
				checkGameFinished();
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




}
