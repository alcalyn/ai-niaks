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
	 * @return Coup g�n�r� avec le chemin interm�diaire si le pion passe par plusieurs cases
	 * @throws IllegalMoveNiaksException si le coup est invalide
	 */
	public Coup coupValide(Coup coup) throws IllegalMoveNiaksException {

		int tailledusaut = 1;
		int i = 0;

		if (coup.getCaseDepart().equals(coup.getCaseArrivee()))	{
			throw new IllegalMoveNiaksException(coup, "Coup impossible,il faut deplacer le pion");
		}

		//else if (!coup.getCaseArrivee().isEmpty())
		else if (!plateau.isEmpty(coup.getCaseArrivee())){
			throw new IllegalMoveNiaksException(coup, "Coup impossible, la case n'est pas libre");
		}
		
		//		else if (coup.getCaseArrivee().x == coup.getCaseDepart().x ){
		//			tailledusaut = coup.getCaseArrivee().y - coup.getCaseDepart().y;
		//
		//			if (!plateau.isEmpty( (coup.getCaseArrivee().x) , ((coup.getCaseArrivee().y + coup.getCaseDepart().y)/2)) ) {	
		//				for(i=0 ; i < tailledusaut/2 ; i++) {			
		//					if((!plateau.isEmpty(   coup.getCaseDepart().x , ((coup.getCaseDepart().y)+i) ))){}
		//					else if (!plateau.isEmpty( ((coup.getCaseArrivee().y)-i) , (coup.getCaseArrivee().x) )){}
		//					else throw new IllegalMoveNiaksException(coup, "Coup impossible, un pion bloque ce coup long");
		//				}
		//			}
		//			else {
		//				throw new IllegalMoveNiaksException(coup, "Coup impossible, pas de pivot pour ce coup long");
		//			}
		//		}

//				else if (coup.getCaseArrivee().y == coup.getCaseDepart().y){
//					tailledusaut = Math.max( coup.getCaseArrivee().x + coup.getCaseDepart().x , Math.max( coup.getCaseArrivee().x - coup.getCaseDepart().x , coup.getCaseDepart().x - coup.getCaseArrivee().x ));
//					if (plateau.isEmpty( (tailledusaut/2) , (coup.getCaseArrivee().y) ) )	{
//						for(i=0 ; i < tailledusaut/2 ; i++){			
//							if((plateau.isEmpty( ((coup.getCaseDepart().x)+i) , coup.getCaseDepart().y ))){}
//							else if (plateau.isEmpty( ((coup.getCaseArrivee().x)-i) , (coup.getCaseArrivee().y) )){}
//							else throw new IllegalMoveNiaksException(coup, "Coup impossible, un pion bloque ce coup long");
//						}
//					}
//					else {
//						throw new IllegalMoveNiaksException(coup, "Coup impossible, pas de pivot pour ce coup long");
//					}
//				}
		
		//		else if (coup.getCaseArrivee().x + 1 == coup.getCaseDepart().x && (coup.getCaseArrivee().y - 1 == coup.getCaseDepart().y)){
		//			tailledusaut = coup.getCaseArrivee().x - coup.getCaseDepart().x - 1;
		//
		//			if (!plateau.isEmpty( (coup.getCaseArrivee().x + coup.getCaseDepart().x)/2) , ((coup.getCaseArrivee().y + coup.getCaseDepart().y)/2) ) ) {	
		//				for(i=0 ; i < tailledusaut/2 ; i++) {			
		//					if((!plateau.isEmpty( ((coup.getCaseDepart().y)+i) , coup.getCaseDepart().x ))){}
		//					else if (!plateau.isEmpty( ((coup.getCaseArrivee().y)-i) , (coup.getCaseArrivee().x) )){}
		//					else throw new IllegalMoveNiaksException(coup, "Coup impossible, un pion bloque ce coup long");
		//				}
		//			}
		//			else {
		//				throw new IllegalMoveNiaksException(coup, "Coup impossible, pas de pivot pour ce coup long");
		//			}
		//		}

		else if (plateau.isEmpty(coup.getCaseArrivee())){
			if ( (coup.getCaseArrivee().x - coup.getCaseDepart().x == 2)        && (coup.getCaseDepart().y == coup.getCaseArrivee().y) 		 && !plateau.isEmpty(coup.getCaseDepart().x + 1 , coup.getCaseDepart().y) ) {}
			else if ( (coup.getCaseArrivee().x - coup.getCaseDepart().x == -2)  && (coup.getCaseDepart().y == coup.getCaseArrivee().y)		 && !plateau.isEmpty(coup.getCaseDepart().x - 1 , coup.getCaseDepart().y) ) {}
			
			else if ( (coup.getCaseArrivee().y - coup.getCaseDepart().y == 2)   && (coup.getCaseDepart().x == coup.getCaseArrivee().x) 		 && !plateau.isEmpty(coup.getCaseDepart().x , coup.getCaseDepart().y + 1) ) {}
			else if ( (coup.getCaseArrivee().y - coup.getCaseDepart().y == -2)  && (coup.getCaseDepart().x == coup.getCaseArrivee().x) 		 && !plateau.isEmpty(coup.getCaseDepart().x , (coup.getCaseDepart().y) - 1) ) {}
			
			else if ( (coup.getCaseArrivee().x == coup.getCaseDepart().x + 2)   && (coup.getCaseArrivee().y == (coup.getCaseDepart().y) - 2) && !plateau.isEmpty(coup.getCaseDepart().x + 1 , (coup.getCaseDepart().y) - 1) ) {}
			else if ( (coup.getCaseArrivee().x == coup.getCaseDepart().x - 2)   && (coup.getCaseArrivee().y == (coup.getCaseDepart().y) + 2) && !plateau.isEmpty(coup.getCaseDepart().x - 1 , (coup.getCaseDepart().y) + 1) ) {}
			
			else if( !( (coup.getCaseArrivee().x - coup.getCaseDepart().x <= 1) && (coup.getCaseDepart().x  - coup.getCaseArrivee().x  <= 1) &&	(coup.getCaseArrivee().y - coup.getCaseDepart().y <= 1) && 	(coup.getCaseDepart().y  - coup.getCaseArrivee().y  <= 1) ) ){
				throw new IllegalMoveNiaksException(coup, "Coup impossible, pour l'instant seul les saut simple sont valide");
			}

			else if( !( (coup.getCaseArrivee().x - coup.getCaseDepart().x <= 1) && (coup.getCaseDepart().x  - coup.getCaseArrivee().x  <= 1) &&	(coup.getCaseArrivee().y - coup.getCaseDepart().y <= 1) && 	(coup.getCaseDepart().y  - coup.getCaseArrivee().y  <= 1) ) ){
				throw new IllegalMoveNiaksException(coup, "Coup impossible, pour l'instant seul les saut simple sont valide");
			}

		}

		return coup;

		//{throw new IllegalMoveNiaksException(coup, "Coup impossible, ce coup successif n'est pas valide");} 

		//		else if (coup.getPion().getJoueur().getBut().x - coup.getCaseArrivee().getCoordCase().x > coup.getPion().getJoueur().getBut().x - coup.getCaseDepart().getCoordCase().x && 
		//				 coup.getPion().getJoueur().getBut().y - coup.getCaseDepart().getCoordCase().y  > coup.getPion().getJoueur().getBut().y - coup.getCaseDepart().getCoordCase().y)
		//		throw new IllegalMoveNiaksException(coup, "Coup impossible, pas de retour en arrière possible");

		//else if(coup.getCaseArrivee().appartienta(liste de case des triangles inerdits))
		//throw new IllegalMoveNiaksException(coup, "Coup impossible, un pion ne peut pas etre arréter dans un des triangles externe");


		//throw new IllegalMoveNiaksException(coup, "Coup impossible, il faut liberer le triangle de depart");
	}


	/**
	 * 
	 * @param coup � faire jouer tout de suite
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
