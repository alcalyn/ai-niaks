package model;

public class Partie extends Model {
	
	
	private Plateau plateau;
	private int taille_plateau;
	private Joueur[] joueurs;
	
	
	
	public Partie(Joueur[] joueurs, int taille_plateau) {
		this.joueurs = joueurs;
		for (Joueur joueur : joueurs) {
			joueur.attachPartie(this);
		}
		this.taille_plateau = taille_plateau;
		this.plateau = new Plateau(taille_plateau, joueurs);
		notifyPions(plateau.getPions());
		
		start();
	}
	
	
	/**
	 * 
	 * @return Coup g�n�r� avec le chemin interm�diaire si le pion passe par plusieurs cases
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public Coup coupValide(Coup coup) throws IllegalMoveNiaksException {
		
		if (coup.getCaseDepart().equals(coup.getCaseArrivee()))
		throw new IllegalMoveNiaksException(coup, "Coup impossible,il faut déplacé le pion");
		
		else if (plateau.isEmpty(coup.getCaseArrivee()))
		throw new IllegalMoveNiaksException(coup, "Coup impossible, la case n'est pas libre");
		
		
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, ce coup succéssif n'est pas valide");
		
		
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, pas de retour en arrière possible");
		
		//else if(coup.getCaseArrivee())
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, un pion ne peut pas etre arréter dans un des triangles externe");
		
		
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, il faut liberer le triangle de départ");
		
		
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, diagonale impossible");		
		
		//else if (plateau.isEmptyLis(coup.getChemin()))
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, une pèce bloque ce coup long");
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, un coup long doit etre symetrique");

		return coup;
	}
	
	
	/**
	 * 
	 * @param coup � faire jouer tout de suite
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public void jouerCoup(Coup coup) throws IllegalMoveNiaksException {
		coup = coupValide(coup);
		
		plateau.movePion(coup.getPion(), coup.getCaseArrivee());
		nextJoueur();
	}
	
	
	/**
	 * 
	 * @param joueur qui vient de jouer
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
				System.out.println("Partie : Erreur, coup instantan� null");
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
		notifyCurrentPlayer(plateau.getJoueur());
		return plateau.getJoueurIndex();
	}



	public Plateau getPlateau() {
		return plateau;
	}


	
	
}
