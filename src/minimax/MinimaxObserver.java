package minimax;

import java.util.ArrayList;

public interface MinimaxObserver {
	
	
	public void updateProcessed(MinimaxNode current, MinimaxNode [] childs, MinimaxNode best_selected);
	
	
}
