package model;

import java.awt.Color;

public abstract class Joueur {
	
	private static int joueur_count = 0;
	
	private String pseudo;
	private Color couleur = null;
	
	
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
		this.setCouleur(defaultColor(joueur_count++));
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
	
	public abstract Coup jouerCoup();
	
	
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
	
	
}
