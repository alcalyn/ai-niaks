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


	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}
	
	public void setCoords(Coords3 coords) {
		this.coords = coords.toCoords();
	}
	
	
	public String toString() {
		return "Pion ["+joueur.getPseudo()+"] : "+coords.toString();
	}
	
}
