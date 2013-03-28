package minimax;

import java.util.ArrayList;

public abstract interface MinimaxNode {
	
	
	
	public abstract ArrayList<MinimaxNode> getChilds();
	
	public abstract double getEval();
	
	public abstract boolean getPlayer();
	
	public abstract boolean equals(MinimaxNode other);
	
	public abstract MinimaxNode clone();
	
}
