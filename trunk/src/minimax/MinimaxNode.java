package minimax;

import java.util.ArrayList;

public abstract class MinimaxNode implements Comparable<MinimaxNode> {
	
	public static final boolean MAX = true, MIN = false;
	
	
	private MinimaxNode parent = null;
	private MinimaxNode[] childs = null;
	private Double eval = null;
	private Double minimax = null;
	private int minimax_depth = -1;
	private Boolean player = null;
	
	
	/**
	 * reinit if you use this node modified since last time
	 * when minimax has been performed.
	 */
	public void reinitMinimaxNode() {
		parent = null;
		childs = null;
		eval = null;
		player = null;
		minimax = null;
	}
	
	
	public final MinimaxNode[] childs() {
		if(childs == null) {
			ArrayList<MinimaxNode> al = getChilds();
			childs = new MinimaxNode[al.size()];
			
			for(int i=0;i< childs.length;i++) {
				MinimaxNode child = al.get(i);
				child.parent = this;
				childs[i] = child;
			}
		}
		
		return childs;
	}
	
	public final MinimaxNode parent() {
		return parent;
	}
	
	public final double eval() {
		if(eval == null) {
			eval = getEval();
		}
		
		return eval;
	}
	
	public final boolean player() {
		if(player == null) {
			player = getPlayer();
		}
		
		return player;
	}
	
	
	public final double minimax(int depth, MinimaxElagator elagator) {
		if(Minimax.stats) MinimaxStats.node_count ++;
		if(Minimax.stats) MinimaxStats.depth(depth);
		
		if(isLastMinimaxSetForDepth(depth)) {
			return minimax.doubleValue();
		}
		
		if((childs().length > 0) && elagator.horizon(this, depth)) {
			for(int i = 0 ; i < childs().length ; i++) {
				double child = childs()[i].minimax(depth + 1, elagator);
				
				if(i == 0) {
					minimax = child;
				} else {
					if(player()) {
						minimax = Math.max(minimax, child);
					} else {
						minimax = Math.min(minimax, child);
					}
				}
				
				if(player()) {
					if((parent() != null) && (parent().minimax != null) && (minimax > parent().minimax)) {
						if(Minimax.stats) MinimaxStats.ab_elagage_count++;
						break;
					}
				} else {
					if((parent() != null) && (parent().minimax != null) && (minimax < parent().minimax)) {
						if(Minimax.stats) MinimaxStats.ab_elagage_count++;
						break;
					}
				}
				
				if(elagator.elage(this, depth)) {
					if(Minimax.stats) MinimaxStats.elagage_count++;
					break;
				}
			}
		} else {
			if(Minimax.stats) MinimaxStats.horizon_size++;
			minimax = eval();
		}
		
		return minimax;
	}
	
	public final double lastMinimax() {
		return minimax.doubleValue();
	}
	
	public final double lastMinimax(int depth) {
		if(isLastMinimaxSetForDepth(depth)) {
			return minimax.doubleValue();
		} else {
			return 0.0;
		}
	}
	
	public final boolean isLastMinimaxSet() {
		return minimax != null;
	}
	
	public final boolean isLastMinimaxSetForDepth(int depth) {
		return minimax != null && depth == minimax_depth;
	}
	
	
	
	
	public final int compareTo(MinimaxNode other) {
		return other.eval() > this.eval() ? 1 : -1 ;
	}
	
	
	protected abstract ArrayList<MinimaxNode> getChilds();
	
	protected abstract double getEval();
	
	protected abstract boolean getPlayer();
	
}
