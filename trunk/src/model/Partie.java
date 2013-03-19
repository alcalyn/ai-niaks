package model;

import exceptions.IllegalMoveNiaksException;

public class Partie {

	private Niaks niaks;

	private Plateau plateau;
	private int taille_plateau;
	private Joueur[] joueurs;



	public Partie(Niaks niaks, Joueur[] joueurs, int taille_plateau) {
		this.niaks = niaks;
		this.joueurs = joueurs;

		for (Joueur joueur : joueurs) {
			joueur.attachPartie(this);
		}

		this.taille_plateau = taille_plateau;
		this.plateau = new Plateau(taille_plateau, joueurs);
		niaks.notifyPions(plateau.getPions());

		start();
	}


	/**
	 * 
	 * @return Coup gï¿½nï¿½rï¿½ avec le chemin intermï¿½diaire si le pion passe par plusieurs cases
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public Coup coupValide(Coup coup) throws IllegalMoveNiaksException {
		Coords depart = coup.getCaseDepart();
		Coords arrivee = coup.getCaseArrivee();
		Joueur joueur = coup.getPion().getJoueur();
		int zone = plateau.getZone(arrivee);
		
		
		if(joueur != getJoueur()) {
			impossible(coup, "Ce n'est pas votre pion");
		}
		
		if(depart.equals(arrivee)) {
			impossible(coup, "Aucun déplacement n'a été enregistré");
		}
		
		if(!plateau.isset(arrivee)) {
			impossible(coup, "Vous sortez du plateau");
		}
		
		if(!plateau.isEmpty(arrivee)) {
			impossible(coup, "La case est déjà occupée");
		}
		
		if((zone != 0) && (zone != joueur.getStartZone()) && (zone != joueur.getEndZone())) {
			impossible(coup, "Vous ne pouvez pas aller sur les autres branches");
		}
		
		for(int i=0;i<6;i++) {
			if(depart.add(Coords.sens(i)).equals(arrivee)) {
				// coup simple
				coup.setSimpleChemin();
				return coup;
			}
		}
		
		// saut multiple
		Coords [] chemin = testSautMultiple(new Coords[] {depart}, arrivee);
		if(chemin != null) {
			coup.setChemin(chemin);
			return coup;
		}
		
		
		impossible(coup, "Coup inattendu");
		return null;
	}
	
	
	private Coords [] testSautMultiple(Coords [] chemin, Coords cible) {
		Coords case_actual = chemin[chemin.length - 1];
		
		if(case_actual.equals(cible)) return chemin;
		
		for(int i=0;i<6;i++) {
			Coords case_sautee = case_actual.add(Coords.sens(i));
			Coords case_next = case_actual.add(Coords.sens(i, 2));
			
			boolean come_back = false;
			
			for(int j=0;j<chemin.length-1;j++) {
				if(case_next.equals(chemin[j])) {
					come_back = true;
					break;
				}
			}
			
			if(!come_back) {
				if((plateau.getZone(case_next) >= 0) && (plateau.getZone(case_sautee) >= 0)) {
					if(!plateau.isEmpty(case_sautee) && plateau.isEmpty(case_next)) {
						Coords [] new_chemin = new Coords[chemin.length + 1];
						
						for(int j=0;j<chemin.length;j++) new_chemin[j] = chemin[j];
						
						new_chemin[new_chemin.length - 1] = case_next;
						
						Coords [] recursion = testSautMultiple(new_chemin, cible);
						if(recursion != null) return recursion;
					}
				}
			}
		}
		
		return null;
	}
	
	
	private void impossible(Coup coup, String cause) throws IllegalMoveNiaksException {
		throw new IllegalMoveNiaksException(coup, cause);
	}


	/**
	 * 
	 * @param coup ï¿½ faire jouer tout de suite
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public void jouerCoup(Coup coup) throws IllegalMoveNiaksException {
		coup = coupValide(coup);

		plateau.movePion(coup.getPion(), coup.getCaseArrivee());
		niaks.notifyPions(plateau.getPions());
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
		plateau.nextJoueur();
		niaks.notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueurIndex();
	}
	
	public int setJoueur(Joueur joueur) {
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
