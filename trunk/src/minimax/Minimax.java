package minimax;

import java.util.ArrayList;
import java.util.Collections;


public class Minimax {
	
	public static final boolean MAX = true, MIN = false;
	
	
	
	
	public static boolean stats = false;
	public static int node_count = 0;
	public static int start_depth;
	public static int max_depth;
	
	private MinimaxElagator defaultElagator = null;
	
	public final MinimaxNode getNext(MinimaxNode current, MinimaxElagator elagator) {
		if(Minimax.stats) node_count = 0;
		if(Minimax.stats) start_depth = 0;
		current.minimax(0, elagator);
		if(Minimax.stats) System.out.println(node_count+" noeuds explorés.");
		if(Minimax.stats) System.out.println("max depth = "+max_depth);
		
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		
		for (MinimaxNode child : current.childs()) {
			if(child.lastMinimax() == current.lastMinimax()) {
				coups.add(child);
			}
		}
		
		Collections.shuffle(coups);
		
		return coups.get(0);
	}

	
	
	
	
	public MinimaxElagator getDefaultElagator() {
		return defaultElagator;
	}
	
	public void setDefaultElagator(MinimaxElagator defaultElagator) {
		this.defaultElagator = defaultElagator;
	}
	
}
