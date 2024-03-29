package minimax;

import java.util.ArrayList;
import java.util.Collections;


public class Minimax {
	
	
	public static boolean multi_thread = true;
	
	public static final boolean
		MAX = true,
		MIN = false;
	
	
	public static boolean stats = false;
	
	
	private MinimaxElagator defaultElagator = null;
	
	private ArrayList<MinimaxObserver> observers = new ArrayList<MinimaxObserver>();
	
	
	
	public final MinimaxNode getNext(MinimaxNode current, MinimaxElagator elagator) {
		if(Minimax.stats) MinimaxStats.init();
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		final MinimaxElagator final_elagator = elagator;
		
		for (final MinimaxNode childs : current.childs()) {
			Thread fred = new Thread(new Runnable() {
				@Override
				public void run() {
					childs.minimax(1, final_elagator);
				}
			});
			
			threads.add(fred);
			
			if(multi_thread) fred.start();
			else fred.run();
		}
		
		if(multi_thread) for (Thread fred : threads) {
			try {
				fred.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		current.minimax(0, elagator);
		
		if(Minimax.stats) MinimaxStats.trace();
		
		MinimaxNode[] childs = current.childs();
		
		if(childs.length == 0) {
			return null;
		}
		
		ArrayList<MinimaxNode> coups = getBestChilds(current);
		
		MinimaxNode best = getBestEval(current, coups);
		
		notifyProcessed(current, childs, best);
		
		return best;
	}
	
	
	
	
	public ArrayList<MinimaxNode> getBestChilds(MinimaxNode current) {
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		
		for (MinimaxNode child : current.childs()) {
			if(child.lastMinimax() == current.lastMinimax()) {
				coups.add(child);
			}
		}
		
		Collections.shuffle(coups);
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
	

	
	public void addObserver(MinimaxObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(MinimaxObserver o) {
		observers.remove(o);
	}
	
	public void notifyProcessed(MinimaxNode current, MinimaxNode [] childs, MinimaxNode best_selected) {
		for (MinimaxObserver observer : observers) {
			observer.updateProcessed(current, childs, best_selected);
		}
	}
	
}
