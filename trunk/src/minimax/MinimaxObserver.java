package minimax;

import java.util.ArrayList;

public interface MinimaxObserver {
	
	
	public void updateProcessed(ArrayList<MinimaxNode> childs, MinimaxNode best_selected);
	
	
}
