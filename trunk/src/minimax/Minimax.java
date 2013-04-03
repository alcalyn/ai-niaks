package minimax;

import java.util.ArrayList;

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
		
		return getBestEval(current, coups);
	}
	
	
	
	
	public ArrayList<MinimaxNode> getBestChilds(MinimaxNode current) {
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		
		System.out.println("     best childs for "+current.player()+" (current minimax = "+current.lastMinimax()+")");
		
		for (MinimaxNode child : current.childs()) {
			System.out.println("         "+((Plateau) child).getLastCoup()+"	; minimax = "+child.lastMinimax()+"	; eval = "+child.eval());
			if(child.lastMinimax() == current.lastMinimax()) {
				coups.add(child);
			}
		}
		
		return coups;
	}
	
	public MinimaxNode getBestEval(MinimaxNode current, ArrayList<MinimaxNode> coups) {
		double best_eval = coups.get(0).eval();
		MinimaxNode best = coups.get(0);
		
		for(int i=1;i<coups.size();i++) {
			MinimaxNode coup = coups.get(i);
			
			if(current.player()) {
				if(coup.eval() > best_eval) {
					best_eval = coup.eval();
					best = coup;
				}
			} else {
				if(coup.eval() < best_eval) {
					best_eval = coup.eval();
					best = coup;
				}
			}
		}
		
		return best;
	}

	
	
	
	
	public MinimaxElagator getDefaultElagator() {
		return defaultElagator;
	}
	
	public void setDefaultElagator(MinimaxElagator defaultElagator) {
		this.defaultElagator = defaultElagator;
	}
	

	
	
}
