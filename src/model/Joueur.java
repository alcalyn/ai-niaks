package model;

import java.awt.Color;
import java.io.Serializable;

public abstract class Joueur implements Serializable {
	
	private static final long serialVersionUID = 7182986241819893533L;
	
	
	
	private String pseudo;
	private Color couleur = null;
	private Partie partie = null;
	private int startZone;
	private int endZone;
	private boolean won = false;
	
	

	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Joueur() {
		this.pseudo = "???";
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
	
	public void setPseudo(String s) {
		this.pseudo = s;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	public void attachPartie(Partie partie) {
		this.partie = partie;
	}
	
	public int getStartZone() {
		return startZone;
	}
	
	public int getEndZone() {
		return endZone;
	}
	
	public void setStartZone(int startZone) {
		this.startZone = startZone;
		this.endZone = (startZone + 3) % 6;
	}
	
	
	public boolean hasWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
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

	
}
