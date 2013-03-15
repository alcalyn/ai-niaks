package model;

import java.util.*;

import niakwork.NiakworkPlayerSocket;

public abstract class Model {
	
	
	private ArrayList<Observer> views = new ArrayList<Observer>();
	
	
	
	public void addObserver(Observer view) {
		views.add(view);
	}
	
	public void removeObserver(Observer view) {
		views.remove(view);
	}
	
	
	
	
	
	protected void notifyProfil(String pseudo) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateProfil(pseudo);
		}
	}
	
	protected void notifyEtat(int etat_partie) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateEtat(etat_partie);
		}
	}
	
	protected void notifyTaillePlateau(int taille) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateTaillePlateau(taille);
		}
	}
	
	protected void notifyJoueurs(Joueur joueurs[]) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateJoueurs(joueurs);
		}
	}
	
	protected void notifyPions(Pion [][] pions) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updatePions(pions);
		}
	}
	
	protected void notifyCurrentPlayer(Joueur joueur) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateCurrentPlayer(joueur);
		}
	}
	
	protected void notifyNiakwork(boolean isEnabled) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakwork(isEnabled);
		}
	}
	
	protected void notifyNiakworkClientFound(NiakworkPlayerSocket npsocket) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakworkClientFound(npsocket);
		}
	}
	
	
	
}
