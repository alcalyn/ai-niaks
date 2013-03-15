package model;

public class Coup {

	private Pion pion = null;
	private Coords case_depart = null;
	private Coords case_arrivee = null;
	private Coords[] chemin = null;
	
	
	public Coup(Pion pion, Coords destination) {
		this.pion = pion;
		this.case_depart = pion.getCoords();
		this.case_arrivee = destination;
	}
	
	
	
	
	public void setChemin(Coords[] chemin) {
		this.chemin = chemin;
	}
	
	public Coords[] getChemin() {
		return chemin;
	}


	public Pion getPion() {
		return pion;
	}

	public Coords getCaseDepart() {
		return case_depart;
	}

	public Coords getCaseArrivee() {
		return case_arrivee;
	}

	public Joueur getJoueur() {
		return pion.getJoueur();
	}
	
}
