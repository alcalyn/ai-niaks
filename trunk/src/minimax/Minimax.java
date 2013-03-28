package minimax;

import java.util.ArrayList;
import java.util.Collections;


public class Minimax {
	
	public static final boolean MAX = true, MIN = false;
	
	private MinimaxNode current = null;
	
	private int default_depth = -1;
	private double default_borne_min = -999999.0;
	private double default_borne_max = +999999.0;
	
	
	public Minimax(MinimaxNode root) {
		this.current = root.clone();
	}
	
	
	public static boolean stats = false;
	public static int node_count = 0;
	public static int start_depth;
	public static int max_depth;
	
	public final MinimaxNode getNext(int depth, double borne_min, double borne_max) {
		if(Minimax.stats) node_count = 0;
		if(Minimax.stats) start_depth = depth;
		current.minimax(depth, borne_min, borne_max);
		if(Minimax.stats) System.out.println(node_count+" noeuds explorés.");
		if(Minimax.stats) System.out.println("max depth = "+max_depth);
		
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		
		for (MinimaxNode child : current.childs()) {
			if(child.lastMinimax() == current.lastMinimax()) {
				coups.add(child);
			}
		}
		
		Collections.shuffle(coups);
		
		current = coups.get(0);
		
		return getCurrent();
	}
	
	public final MinimaxNode getNext() {
		return getNext(default_depth, default_borne_min, default_borne_max);
	}
	
	public final MinimaxNode setNext(MinimaxNode node) {
		for (MinimaxNode child : current.childs()) {
			if(child.equals(node)) {
				current = child;
			}
		}
		
		return getCurrent();
	}
	
	public MinimaxNode getCurrent() {
		return current.clone();
	}
	
	
	public int default_depth(int depth) {
		return default_depth = depth;
	}
	
	public int default_depth() {
		return default_depth;
	}
	
	
	public void default_bornes(double borne_min, double borne_max) {
		default_borne_min = borne_min;
		default_borne_max = borne_max;
	}
	
	
}
