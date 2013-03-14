package model;

import java.util.ArrayList;

import exceptions.PartieNotReadyToStartNiaksException;

public class PartiePreparator {
	
	private Niaks niaks;
	
	private Humain host = null;
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	private int taille_plateau = 4;
	
	
	
	public PartiePreparator(Niaks niaks, Humain host) {
		this.niaks = niaks;
		this.host = host;
		addJoueur(host);
	}
	
	public Partie createPartie() throws PartieNotReadyToStartNiaksException {
		if(joueurs.size() > 6) {
			throw new PartieNotReadyToStartNiaksException("Il y a trop de joueurs");
		}
		
		if(joueurs.size() == 5) {
			throw new PartieNotReadyToStartNiaksException("Il ne peut pas y avoir 5 joueurs");
		}
		
		Joueur js [] = new Joueur[getNbJoueur()];
		joueurs.toArray(js);
		return new Partie(niaks, js, taille_plateau);
	}
	
	
	public void setPlateauSize(int taille) {
		if(taille > 0) {
			this.taille_plateau = taille;
		}
	}
	
	public Humain getHost() {
		return host;
	}
	
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}
	
	public void addJoueur(Joueur j) {
		joueurs.add(j);
	}
	
	public int getNbJoueur() {
		return joueurs.size();
	}
	
	

}
