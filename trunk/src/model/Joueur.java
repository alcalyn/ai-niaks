package model;

import java.awt.Color;

public abstract class Joueur {
	
	private String pseudo;
	private Color couleur = null;
	private Partie partie = null;
	private Coords coordBut = null;
	
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Color getCouleur() {
		return couleur;
	}
	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	public void attachPartie(Partie partie) {
		this.partie = partie;
	}
	
	
	
	public abstract Coup jouerCoup();
	
	public abstract boolean playsInstantly();
	
	
	
	
	
	public static Color defaultColor(int c) {
		return new Color[]{
				new Color(0x0000FF),
				new Color(0xFFFF00),
				new Color(0x00FF00),
				new Color(0xFF0000),
				new Color(0xFFFFFF),
				new Color(0x000000),
		}[c];
	}

	public Coords getBut() {
		return coordBut;
	}
	
	public void setBut(Coords c) {
		this.coordBut=c;
	}	
}
