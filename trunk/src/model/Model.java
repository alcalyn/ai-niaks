package model;

import java.util.*;

import niakwork.NiakworkHostSocket;
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
	
	protected void notifyJoueurWon(Joueur joueur) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateJoueurWon(joueur);
		}
	}
	
	protected void notifyNiakwork(boolean isEnabled) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakwork(isEnabled);
		}
	}
	
	protected void notifyNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakworkClientWantJoin(npsocket, pseudo);
		}
	}
	
	protected void notifyNiakworkServerFound(NiakworkHostSocket nssocket, String pseudo) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakworkServerFound(nssocket, pseudo);
		}
	}
	
	protected void notifyNiakworkHostDenied(NiakworkHostSocket nssocket, String reason) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakworkHostDenied(nssocket, reason);
		}
	}
	
	protected void notifyNiakworkHostAccept(NiakworkHostSocket nssocket) {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateNiakworkHostAccept(nssocket);
		}
	}
	
	protected void notifyGameFinished() {
		for(int i=0;i<views.size();i++) {
			Observer o = views.get(i);
			o.updateGameFinished();
		}
	}
	
	
	
	
}
