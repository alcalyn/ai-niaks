package minimax;

import java.util.ArrayList;
import java.util.Collections;


public class Minimax {
	
	public static final boolean MAX = true, MIN = false;
	
	public static boolean stats = true;
	
	
	private MinimaxElagator defaultElagator = null;
	
	
	
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
		
		for (MinimaxNode child : current.childs()) {
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
