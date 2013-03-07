package minimax;

import java.util.ArrayList;

public abstract class MinimaxNode implements Comparable<MinimaxNode> {
	
	public static final boolean MAX = true, MIN = false;
	
	
	private MinimaxNode parent = null;
	private MinimaxNode[] childs = null;
	private Double eval = null;
	private Boolean player = null;
	private Double minimax = null;
	
	
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
	
	
	public final double minimax(int depth, double borne_min, double borne_max) {
		if(Minimax.stats) Minimax.node_count ++;
		if(Minimax.stats) Minimax.max_depth = Math.max(Minimax.start_depth - depth, Minimax.start_depth);
		
		if((depth != 0) && (childs().length > 0)) {
			for(int i = 0 ; i < childs().length ; i++) {
				double child = childs()[i].minimax(depth - 1, borne_min, borne_max);
				
				if(i == 0) {
					minimax = child;
				} else {
					if(player()) {
						minimax = Math.max(minimax, child);
					} else {
						minimax = Math.min(minimax, child);
					}
				}
				
				if(true) {
					if(player()) {
						if(minimax >= borne_max) break;
						if((parent() != null) && (parent().minimax != null) && (minimax > parent().minimax)) break;
					} else {
						if(minimax <= borne_min) break;
						if((parent() != null) && (parent().minimax != null) && (minimax < parent().minimax)) break;
					}
				}
			}
		} else {
			minimax = eval();
		}
		
		return minimax;
	}
	
	public final double lastMinimax() {
		return minimax;
	}
	
	
	
	
	public final int compareTo(MinimaxNode other) {
		return other.eval() > this.eval() ? 1 : -1 ;
	}
	
	
	protected abstract ArrayList<MinimaxNode> getChilds();
	
	protected abstract double getEval();
	
	protected abstract boolean getPlayer();
	
	public abstract boolean equals(MinimaxNode other);
	
	public abstract MinimaxNode clone();
	
}