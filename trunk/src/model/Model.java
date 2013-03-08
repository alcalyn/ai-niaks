package model;

import java.util.*;

public abstract class Model {
	
	
	private ArrayList<Observer> views = new ArrayList<Observer>();
	
	
	
	public void addObserver(Observer view) {
		views.add(view);
	}
	
	
	protected void notifyPions(Pion [][] pions) {
		for (Observer o : views) {
			o.updatePions(pions);
		}
	}
	
	protected void notifyCurrentPlayer(Joueur joueur) {
		for (Observer o : views) {
			o.updateCurrentPlayer(joueur);
		}
	}
	
	
	
}
