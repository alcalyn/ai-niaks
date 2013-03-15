package model;

public class Coup {
	
	
	private Pion pion = null;
	private Case case_depart = null;
	private Case case_arrivee = null;
	private Coords[] chemin = null;
	
	
	public Coup(Pion pion, Coords destination) {
		this.pion = pion;
		this.case_depart = new Case (pion.getCoords());
		this.case_arrivee = new Case (destination);
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

	public Case getCaseDepart() {
		return case_depart;
	}

	public Case getCaseArrivee() {
		return case_arrivee;
	}

	public Joueur getJoueur() {
		return pion.getJoueur();
	}
	
}
