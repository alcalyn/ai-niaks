package model;

import java.io.Serializable;
import java.util.ArrayList;

import exceptions.PartieNotReadyToStartNiaksException;

public class PartiePreparator implements Serializable {
	
	private static final long serialVersionUID = 6254308576682141259L;
	
	

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
		if(joueurs.size() == 0) {
			throw new PartieNotReadyToStartNiaksException("Il n'y a aucun joueur");
		}
		
		if(joueurs.size() > 6) {
			throw new PartieNotReadyToStartNiaksException("Il y a trop de joueurs");
		}
		
		if(joueurs.size() == 5) {
			throw new PartieNotReadyToStartNiaksException("Il ne peut pas y avoir 5 joueurs");
		}
		
		if(joueurs.size() > 2) {
			for (Joueur j : joueurs) {
				if(j instanceof Ordinateur) {
					throw new PartieNotReadyToStartNiaksException("Les ordinateurs ne peuvent jouer qu'en duel");
				}
			}
		}
		
		Joueur js [] = new Joueur[getNbJoueur()];
		joueurs.toArray(js);
		return new Partie(niaks, js, taille_plateau);
	}
	
	
	public void setPlateauSize(int taille) {
		if(taille > 0) {
			this.taille_plateau = taille;
			niaks.notifyTaillePlateau(taille);
		}
	}
	
	public int getPlateauSize() {
		return this.taille_plateau;
	}
	
	public Humain getHost() {
		return host;
	}
	
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}
	
	public void addJoueur(Joueur j) {
		joueurs.add(j);
		setDefaultColor();
		niaks.notifyJoueurs(getArrayJoueurs());
	}
	
	public void addJoueur(Joueur j, int at) {
		joueurs.add(at, j);
		setDefaultColor();
		niaks.notifyJoueurs(getArrayJoueurs());
	}
	
	public void removeJoueur(Joueur j) {
		joueurs.remove(j);
		setDefaultColor();
		niaks.notifyJoueurs(getArrayJoueurs());
	}
	
	public void removeJoueur(int idx) {
		if(idx < joueurs.size()) {
			joueurs.remove(idx);
			setDefaultColor();
			niaks.notifyJoueurs(getArrayJoueurs());
		}
	}
	
	public void removeAllJoueur() {
		joueurs = new ArrayList<Joueur>();
		niaks.notifyJoueurs(getArrayJoueurs());
	}
	
	public void setDefaultColor() {
		for(int i=0;i<joueurs.size();i++) {
			joueurs.get(i).setCouleur(Joueur.defaultColor(i));
		}
	}
	
	
	
	private Joueur[] getArrayJoueurs() {
		Joueur [] ret = new Joueur[joueurs.size()];
		joueurs.toArray(ret);
		return ret;
	}
	
	public int getNbJoueur() {
		return joueurs.size();
	}
	
	public Niaks getNiaks() {
		return niaks;
	}

	public void removeJoueurs() {
		joueurs = new ArrayList<Joueur>();
	}
	
	

}
