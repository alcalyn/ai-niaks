package minimax;

import java.util.ArrayList;
import java.util.Collections;

import model.Plateau;


public class Minimax {
	
	public static final boolean MAX = true, MIN = false;
	
	public static boolean stats = true;
	
	
	private MinimaxElagator defaultElagator = null;
	
	private ArrayList<MinimaxObserver> observers = new ArrayList<MinimaxObserver>();
	
	
	
	public final MinimaxNode getNext(MinimaxNode current, MinimaxElagator elagator) {
		if(Minimax.stats) MinimaxStats.init();
		current.minimax(0, elagator);
		if(Minimax.stats) MinimaxStats.trace();
		
		ArrayList<MinimaxNode> coups = getBestChilds(current);
		
		Collections.shuffle(coups);
		
		return coups.get(0);
	}
	
	
	
	
	public ArrayList<MinimaxNode> getBestChilds(MinimaxNode current) {
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		
		System.out.println("===== best childs =====");
		
		for (MinimaxNode child : current.childs()) {
			System.out.println("     "+((Plateau) child).getLastCoup()+" ; minimax = "+child.lastMinimax()+" ; eval = "+child.eval());
			if(child.lastMinimax() == current.lastMinimax()) {
				coups.add(child);
			}
		}
		
		return coups;
	}

	
	
	
	
	public MinimaxElagator getDefaultElagator() {
		return defaultElagator;
	}
	
	public void setDefaultElagator(MinimaxElagator defaultElagator) {
		this.defaultElagator = defaultElagator;
	}
	

	
	
}
