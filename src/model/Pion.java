package model;

import java.awt.Color;


public class Pion {
	
	public static final char
		BLEU	= 1,	JAUNE	= 2,
		ROUGE	= 3,	VERT	= 4,
		BLANC	= 5,	NOIR	= 6,
		VIDE = 0;
	
	
	private Joueur joueur;
	private Coords coords;


	public Pion(Joueur joueur) {
		this.joueur = joueur;
	}
	
	public Pion(Pion copy) {
		this.joueur = copy.joueur;
		this.coords = new Coords(copy.coords);
	}


	public Color getCouleur() {
		return joueur.getCouleur();
	}


	public Joueur getJoueur() {
		return joueur;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}
	
	public String toString() {
		return "Pion ["+joueur.getPseudo()+"] : "+coords.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Pion) {
			Pion other = (Pion) o;
			
			return (
					joueur == other.joueur &&
					coords.equals(other.coords)
			);
		} else {
			return false;
		}
	}
	
}
