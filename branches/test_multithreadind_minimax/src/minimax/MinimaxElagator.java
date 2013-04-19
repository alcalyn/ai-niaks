package minimax;

public interface MinimaxElagator {
	
	/**
	 * 
	 * @param node current node to check
	 * @param depth current depth
	 * @return boolean true if minimax continues running on this node. Else return eval value
	 * 
	 * Useful to limit max depth
	 */
	public boolean horizon(MinimaxNode node, int depth);
	
	
	/**
	 * 
	 * @param node
	 * @param depth
	 * @return boolean true will stop childs exploration
	 * 
	 * Useful to stop childs having min or max minimax value 
	 */
	public boolean elage(MinimaxNode node, int depth);
	
}
