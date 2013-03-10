package model;

public class Plateau {
	
	private Joueur[] joueurs;
	
	private int taille;
	private Pion[][] pions;
	private int joueur;
	private Pion[] cases;
	
	
	public Plateau(int taille, Joueur[] joueurs) {
		this.taille = taille;
		this.joueurs = joueurs;
		joueur = 0;
		initPions();
		initCases();
		placerPions();
	}
	
	public Plateau(Plateau copy) {
		this.taille = copy.taille;
		this.joueur = copy.joueur;
		this.joueurs = copy.joueurs;
		this.cases = new Pion[copy.cases.length];
		
		int nb_joueur = copy.getNbJoueur();
		int nb_pion = (copy.taille * (copy.taille + 1)) / 2;
		
		pions = new Pion[nb_joueur][nb_pion];
		
		for(int i = 0;i<nb_joueur;i++) {
			for(int j = 0;j<nb_pion;j++) {
				pions[i][j] = new Pion(copy.pions[i][j]);
				Coords c = copy.pions[i][j].getCoords();
				cases[indexOf(c.x, c.y)] = pions[i][j];
			}
		}
	}
	
	private void initPions() {
		int nb_joueur = getNbJoueur();
		int nb_pion = (taille * (taille + 1)) / 2;
		
		pions = new Pion[nb_joueur][nb_pion];
		
		for(int i = 0;i<nb_joueur;i++) {
			for(int j = 0;j<nb_pion;j++) {
				pions[i][j] = new Pion(joueurs[i]);
			}
		}
	}
	
	private void initCases() {
		cases = new Pion[getCaseMatriceLength()];
	}
	
	private void placerPions() {
		switch(getNbJoueur()) {
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
		int k = 0;
		
		for(int j = 1; j <= taille; j++) {
			for(int i = j; i <= taille; i++) {
				int x = i;
				int y = taille + j - i;
				
				Coords c = new Coords(x, y);
				Pion pion = getPion(joueur, k++);
				movePion(pion, branche.mul(c).toCoords());
			}
		}
	}
	
	
	public Pion getPion(char joueur, int i) {
		return pions[joueur - 1][i];
	}
	
	public Pion getCase(Coords3 c) {
		return getCase(c.toCoords());
	}
	
	public Pion getCase(Coords c) {
		return cases[indexOf(c.x, c.y)];
	}
	
	public void movePion(Pion pion, Coords coords) {
		if(cases[indexOf(coords)] == null) {
			if(pion.getCoords() != null) {
				cases[indexOf(pion.getCoords())] = null;
			}
			
			pion.setCoords(coords);
			cases[indexOf(coords)] = pion;
		}
	}
	
	public boolean isset(Coords3 c) {
		return isset(c.toCoords());
	}
	
	public boolean isset(Coords c) {
		// TODO indiquer si une case existe
		return true;
	}
	
	private int indexOf(int x, int y) {
		//return 4 * taille * (2 * taille + y + 1) + x + y;
		int _x = x + 2 * taille;
		int _y = y + 2 * taille;
		int module = 4 * taille + 1;
		return _y * module + _x;
	}
	
	private int getCaseMatriceLength() {
		 // crade...
		int nb = taille * 4 + 1;
		return nb * nb;
	}
	
	private int indexOf(Coords coords) {
		return indexOf(coords.x, coords.y);
	}
	
	public Pion [][] getPions() {
		return pions;
	}

	public int getJoueur() {
		return joueur;
	}
	
	public int getNbJoueur() {
		return joueurs.length;
	}

	public void nextJoueur() {
		if(++joueur >= getNbJoueur()) joueur = 0;
	}
	
	
	
}