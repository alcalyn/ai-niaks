package model;

import java.util.ArrayList;

import exceptions.IllegalMoveNiaksException;

import minimax.MinimaxNode;

public class Plateau extends MinimaxNode {
	
	private Joueur[] joueurs;
	
	private int taille;
	private Pion[][] pions;
	private int joueur;
	private Pion[] cases;
	private int tour = 0;
	private Coup last_coup = null;

	private boolean isFinished = false;
	
	
	public Plateau(int taille, Joueur[] joueurs) {
		initPlateau(taille, joueurs);
		joueur = (int) Math.floor(Math.random() * joueurs.length);
	}
	
	
	private void initPlateau(int taille, Joueur[] joueurs) {
		this.taille = taille;
		this.joueurs = joueurs;
		initPions();
		initCases();
		placerPions();
	}
	
	public Plateau(Plateau copy) {
		this.taille = copy.taille;
		this.joueur = copy.joueur;
		this.joueurs = copy.joueurs;
		this.cases = new Pion[copy.cases.length];
		this.last_coup = copy.last_coup == null ? null : new Coup(copy.last_coup) ;
		this.isFinished = copy.isFinished;
		this.tour = copy.tour;
		
		int nb_joueur = copy.getNbJoueur();
		int nb_pion = (copy.taille * (copy.taille + 1)) / 2;
		
		this.pions = new Pion[nb_joueur][nb_pion];
		
		for(int i = 0;i<nb_joueur;i++) {
			for(int j = 0;j<nb_pion;j++) {
				this.pions[i][j] = new Pion(copy.pions[i][j]);
				Coords c = this.pions[i][j].getCoords();
				this.cases[indexOf(c.x, c.y)] = this.pions[i][j];
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
				joueurs[0].setStartZone(1);
				break;
			
			case 2:
				placerPions(1, 1);
				placerPions(2, 4);
				joueurs[0].setStartZone(1);
				joueurs[1].setStartZone(4);
				break;
				
			case 3:
				placerPions(1, 1);
				placerPions(2, 3);
				placerPions(3, 5);
				joueurs[0].setStartZone(1);
				joueurs[1].setStartZone(3);
				joueurs[2].setStartZone(5);
				break;
			
			case 4:
				placerPions(1, 1);
				placerPions(2, 2);
				placerPions(3, 4);
				placerPions(4, 5);
				joueurs[0].setStartZone(1);
				joueurs[1].setStartZone(2);
				joueurs[2].setStartZone(4);
				joueurs[3].setStartZone(5);
				break;
			
			case 6:
				for(int i=0;i<6;i++) {
					placerPions(i+1, i+1);
					joueurs[i].setStartZone(i+1);
				}
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
				movePion(pion, branche.mul(c).toCoords() );			
			}
		}
	}
	
	public boolean isCoupValide(Coup coup) {
		try {
			coupValide(coup);
			return true;
		} catch (IllegalMoveNiaksException e) {
			return false;
		}
		
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
		int zone = getZone(arrivee);


		if(joueur != getJoueur()) {
			impossible(coup, "Ce n'est pas votre pion", false);
		}

		if(depart.equals(arrivee)) {
			impossible(coup, "Aucun déplacement n'a été enregistré", false);
		}

		if(!isset(arrivee)) {
			impossible(coup, "Vous sortez du plateau", false);
		}

		if(!isEmpty(arrivee)) {
			impossible(coup, "La case est déjà occupée", false);
		}

		if(!joueurPeutAller(joueur, zone)) {
			impossible(coup, "Vous ne pouvez pas aller sur les autres branches");
		}
		
		if((getZone(depart) != joueur.getStartZone()) && (getZone(arrivee) == joueur.getStartZone())) {
			impossible(coup, "Impossible de revenir dans votre branche pour bloquer votre opposant");
		}

		// coup simple
		for(int i=0;i<6;i++) {
			if(depart.add(Coords.sens(i)).equals(arrivee)) {
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
		
		if(getPartie().isCoupLongs()) {
			// saut long
			for(int sens=0;sens<6;sens++) {
				for(int taille=4;taille<getTaille()*4;taille+=2) {
					if(depart.add(Coords.sens(sens, taille)).equals(arrivee)) {
						Coords middle = depart.add(Coords.sens(sens, taille / 2));
						
						for(int i=1;i<taille-1;i++) {
							Coords c = depart.add(Coords.sens(sens, i));
							
							if(c.equals(middle) == isEmpty(c)) {
								impossible(coup, "Coup long invalide");
							}
						}
						
						coup.setSimpleChemin();
						return coup;
					}
				}
			}
		}
		
		if(getPartie().isMultipleCoupLongs()) {
			// variante des coups longs multiple
			// TODO
		}

		impossible(coup, "Coup invalide");
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
				if((getZone(case_next) >= 0) && (getZone(case_sautee) >= 0)) {
					if(!isEmpty(case_sautee) && isEmpty(case_next)) {
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


	private void impossible(Coup coup, String cause, boolean important) throws IllegalMoveNiaksException {
		throw new IllegalMoveNiaksException(coup, cause, important);
	}
	private void impossible(Coup coup, String cause) throws IllegalMoveNiaksException {
		throw new IllegalMoveNiaksException(coup, cause);
	}
	
	public Pion getPion(char joueur, int i) {
		return pions[joueur - 1][i];
	}
	
	public Pion getPion(int joueur, int i) {
		return pions[joueur][i];
	}
	
	public Pion getPion(Joueur joueur, int i) {
		return getPions(joueur)[i];
	}
	
	public Pion [] getPions(Joueur joueur) {
		for(int i=0;i<getNbJoueur();i++) {
			if(joueur == joueurs[i]) {
				return pions[i];
			}
		}
		
		return null;
	}
	
	public Pion getCase(Coords3 c) {
		return getCase(c.toCoords());
	}
	
	public boolean isEmpty(Coords3 coords){
		return getCase(coords.toCoords()) == null;
	}
	
	public boolean isEmpty(Coords coords){
		return getCase(coords)==null;
	}
	
	public boolean isEmpty(int x , int y){
		return getCase(x,y)==null;
	}
	
	public Pion getCase(Coords c) {
		return cases[indexOf(c.x, c.y)];
	}
	
	public Pion getCase(int x , int y) {
		return cases[indexOf(x, y)];
	}
	
	public void movePion(Pion pion, Coords destination) {
		if(isEmpty(destination)) {
			last_coup = null;
			
			if(pion.getCoords() != null) {
				cases[indexOf(pion.getCoords())] = null;
				last_coup = new Coup(pion, destination);
			}
			
			pion.setCoords(destination);
			cases[indexOf(destination)] = pion;
			tour++;
		}
	}
	
	public Coup getLastCoup() {
		return last_coup;
	}
	
	public boolean isset(Coords3 c) {
		return isset(c.toCoords());
	}
	
	public boolean isset(Coords c) {
		return getZone(c) >= 0;
	}

	private int indexOf(int x, int y) {
		int _x = x + 2 * taille;
		int _y = y + 2 * taille;
		
		if(_x < taille) {
			_x += taille + 1;
			_y += taille;
		}
		
		if(_x > 3*taille) {
			_x -= taille;
			_y += 2*taille + 1;
		}
		
		_x -= taille;
		
		
		int module = 2 * taille + 1;
		return _y * module + _x;
	}
	
	private int getCaseMatriceLength() {
		return 8 * (taille + 1) * (taille + 1);
	}
	
	private int indexOf(Coords coords) {
		return indexOf(coords.x, coords.y);
	}
	
	public Pion [][] getPions() {
		return pions;
	}

	public Joueur getJoueur() {
		return joueurs[joueur];
	}
	
	public int getJoueurIndex() {
		return joueur;
	}
	
	public int getNbJoueur() {
		return joueurs.length;
	}

	public void nextJoueur() {
		if(++joueur >= getNbJoueur()) joueur = 0;
	}
	
	public void setJoueur(Joueur joueur) {
		int i = 0;
		for (Joueur j : joueurs) {
			if(j == joueur) {
				this.joueur = i;
				break;
			}
			
			i++;
		}
	}
	
	public Partie getPartie() {
		return getJoueur().getPartie();
	}
	
	
	/**
	 * 
	 * @param c Coords de la case à tester
	 * @return int :	0 si c est dans l'hexagone central,
	 * 					1-6 si dans une branche,
	 * 					-1 si en dehors du plateau
	 */
	public int getZone(Coords c){
		Coords3 c3 = c.toCoords3();
		int distance = c3.distance();
		
		if(distance <= taille) {
			return 0;
		}
		
		if(distance <= taille * 2) {
			if((Math.abs(c3.x) <= taille) && (Math.abs(c3.y) <= taille) && (Math.abs(c3.z) <= taille)) {
				if((c3.x > 0) && (c3.y > 0)) return 1;
				if((c3.y > 0) && (c3.z > 0)) return 2;
				if((c3.x < 0) && (c3.z > 0)) return 3;
				if((c3.x < 0) && (c3.y < 0)) return 4;
				if((c3.y < 0) && (c3.z < 0)) return 5;
				if((c3.x > 0) && (c3.z < 0)) return 6;
			}
		}
		
		return -1;
	}
	
	public boolean joueurPeutAller(Joueur j, int zone) {
		return (zone == 0) || (zone == j.getStartZone()) || (zone == j.getEndZone());
	}

	public  int getTaille() {
		return taille;
	}

	@Override
	protected ArrayList<MinimaxNode> getChilds() {
		ArrayList<MinimaxNode> childs = new ArrayList<MinimaxNode>();
		Joueur joueur = getJoueur();
		
		if(!isFinished()) {
			for(int i=-taille*2;i<=taille*2;i++) for(int j=-taille*2;j<=taille*2;j++) {
				Coords c = new Coords(i, j);
				
				if(isset(c) && isEmpty(c) && joueurPeutAller(joueur, getZone(c))) {
					Pion [] pions = getPions(joueur);
					for(int k=0;k<pions.length;k++) {
						Pion p = pions[k];
						Coup coup = new Coup(p, c);
						
						if(isCoupValide(coup)) {
							Plateau next = new Plateau(this);
							next.movePion(next.getPion(joueur, k), c);
							next.nextJoueur();
							if(hasWon(next.getJoueur())) {
								next.nextJoueur();
							}
							
							childs.add(next);
						}
					}
				}
			}
		}
		
		return childs;
	}

	@Override
	protected double getEval() {
		Joueur current = getPartie().getJoueur();
		
		if(current instanceof Ordinateur) {
			Ordinateur ordi = (Ordinateur) current;
			
			return joueurs.length == 1 ?
					ordi.getStrategy().calc(this, joueurs[0]) :
					ordi.getStrategy().calc(this, joueurs[0], joueurs[1]);
		} else {
			return joueurs.length == 1 ?
					Strategies.getDefaultStrategy().calc(this, joueurs[0]) :
					Strategies.getDefaultStrategy().calc(this, joueurs[0], joueurs[1]);
		}
	}

	@Override
	protected boolean getPlayer() {
		return joueur == 0;
	}
	
	
	
	public int evalJoueur(Joueur joueur){
		int sum = 0;
		
		for (Pion p : getPions(joueur)) {
			sum += evalPion(p);
		}
		
		sum -= getMinimumEval();
		
		return sum;
	}
	
	public int evalPion(Pion p) {
		Coords from = p.getCoords();
		Coords to = getBranchExtremeCoords(p.getJoueur().getEndZone());
		
		return from.distance(to);
	}
	
	public Coords getBranchExtremeCoords(int zone) {
		switch(zone) {
			case 1: return new Coords(taille, taille);
			case 2: return new Coords(-taille, 2*taille);
			case 3: return new Coords(-2*taille, taille);
			case 4: return new Coords(-taille, -taille);
			case 5: return new Coords(taille, -2*taille);
			case 6: return new Coords(2*taille, -taille);
			
			default: return null;
		}
	}
	
	public int getMinimumEval() {
		return getPartie().getMinimumEval();
	}
	
	public boolean hasWon(Joueur joueur) {
		Pion [] pions = getPions(joueur);

		for (Pion pion : pions) {
			if(getZone(pion.getCoords()) != joueur.getEndZone()) {
				return false;
			}
		}

		return true;
	}
	
	public void checkGameFinished() {
		for (Joueur joueur : joueurs) {
			for (Pion p : getPions(joueur)) {
				if(getZone(p.getCoords()) != joueur.getEndZone()) {
					return;
				}
			}
		}

		isFinished = true;
	}
	
	public boolean isFinished() {
		checkGameFinished();
		return isFinished;
	}
	
	public String toString() {
		String s = "Plateau (taille "+taille+")\n";
		
		for (Joueur j : getPartie().getJoueurs()) {
			s += "     - Pions de "+j+" :\n";
			for (Pion p : getPions(j)) {
				s += "           "+p+"\n";
			}
		}
		
		return s;
	}
	
	public int getTour() {
		return tour;
	}
	
}
