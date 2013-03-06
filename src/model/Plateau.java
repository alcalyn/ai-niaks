package model;

public class Plateau {
	
	
	private Partie partie;
	private Pion[][] pions;
	private char joueur;
	
	
	public Plateau(Partie partie) {
		this.partie = partie;
		joueur = 1;
		initPions();
		placerPions();
	}
	
	private void initPions() {
		int nb_joueur = partie.getNbJoueur();
		int taille_plateau = partie.getTaillePlateau();
		int nb_pion = (taille_plateau * (taille_plateau + 1)) / 2;
		
		pions = new Pion[nb_joueur][nb_pion];
		
		for(int i = 0;i<nb_joueur;i++) {
			for(int j = 0;j<nb_pion;j++) {
				pions[i][j] = new Pion((char) (i + 1));
			}
		}
	}
	
	private void placerPions() {
		switch(partie.getNbJoueur()) {
			case 1:
				placerPions(1, 1);
				break;
			
			case 2:
				placerPions(1, 1);
				placerPions(2, 4);
				break;
				
			case 3:
				placerPions(1, 1);
				placerPions(2, 3);
				placerPions(3, 5);
				break;
			
			case 4:
				placerPions(1, 1);
				placerPions(2, 2);
				placerPions(3, 4);
				placerPions(4, 5);
				break;
			
			case 6:
				placerPions(1, 1);
				placerPions(2, 2);
				placerPions(3, 3);
				placerPions(4, 4);
				placerPions(5, 5);
				placerPions(6, 6);
				break;
		}
	}
	
	private void placerPions(int joueur, int branche) {
		Coords3 b = null;
		
		switch(branche) {
			case 1:
				b = new Coords3(1, 1, 0);
				break;
			
			case 2:
				b = new Coords3(0, 1, 1);
				break;
			
			case 3:
				b = new Coords3(-1, 0, 1);
				break;
			
			case 4:
				b = new Coords3(-1, -1, 0);
				break;
			
			case 5:
				b = new Coords3(0, -1, -1);
				break;
			
			case 6:
				b = new Coords3(1, 0, -1);
				break;
		}
		
		placerPions((char) joueur, b);
	}
	
	private void placerPions(char joueur, Coords3 branche) {
		int taille_plateau = partie.getTaillePlateau();
		
		int k = 0;
		
		for(int i = taille_plateau * 2; i > taille_plateau; i--) {
			for(int j = 0; j <= taille_plateau * 2 - i; j++) {
				Coords c = new Coords(taille_plateau - k, i - taille_plateau + k);
				getPion(joueur, k).setCoords(branche.mul(c));
				k++;
			}
		}
	}
	
	
	public Pion getPion(char joueur, int i) {
		return pions[joueur - 1][i];
	}
	
	public Pion [][] getPions() {
		return pions;
	}

	public char getJoueur() {
		return joueur;
	}

	public void nextJoueur() {
		if(++joueur > partie.getNbJoueur()) joueur = 1;
	}
	
	
	
}
