package model;


public class Pion {
	
	public static final char
		BLEU	= 1,	JAUNE	= 2,
		ROUGE	= 3,	VERT	= 4,
		BLANC	= 5,	NOIR	= 6,
		VIDE = 0;
	
	
	private char couleur;
	private Coords coords;


	public Pion(char couleur) {
		this.couleur = couleur;
	}


	public char getCouleur() {
		return couleur;
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
		return "Pion ["+(int) couleur+"] : "+coords.toString();
	}
	
}
