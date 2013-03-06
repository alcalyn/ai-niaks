package model;

public class Pion {
	
	public static final char
		BLEU	= 1,	JAUNE	= 2,
		ROUGE	= 3,	VERT	= 4,
		BLANC	= 5,	NOIR	= 6,
		VIDE = 0;
	
	
	private char couleur;


	public Pion(char couleur) {
		this.couleur = couleur;
	}
	
	
}
