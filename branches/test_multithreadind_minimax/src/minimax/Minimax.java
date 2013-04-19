package minimax;

import java.util.ArrayList;
import java.util.Collections;


public class Minimax {
	
	public static final boolean
		MAX = true,
		MIN = false;
	
	
	public static boolean stats = false;
	
	
	private MinimaxElagator defaultElagator = null;
	
	private ArrayList<MinimaxObserver> observers = new ArrayList<MinimaxObserver>();
	
	
	
	public final MinimaxNode getNext(MinimaxNode current, MinimaxElagator elagator) {
		if(Minimax.stats) MinimaxStats.init();
		
		MinimaxNode [] childs = current.childs();
		ArrayList<MinimaxThread> threads = new ArrayList<MinimaxThread>();
		
		for (MinimaxNode child : childs) {
			threads.add(new MinimaxThread(child, elagator));
		}
		
		while(!checkDone(threads)) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(threads.size()+" done");
		
		if(Minimax.stats) MinimaxStats.trace();
		
		if(childs.length == 0) {
			return null;
		}
		
		ArrayList<MinimaxNode> coups = getBestChilds(current);
		
		MinimaxNode best = getBestEval(current, coups);
		
		notifyProcessed(current, childs, best);
		
		return best;
	}
	
	private boolean checkDone(ArrayList<MinimaxThread> threads) {
		for (MinimaxThread minimaxThread : threads) {
			if(!minimaxThread.isDone()) {
				return false;
			}
		}
		
		return true;
	}
	
	
	private static class MinimaxThread extends Thread {
		
		private MinimaxNode node;
		private MinimaxElagator elagator;
		private boolean done = false;
		
		public MinimaxThread(MinimaxNode node, MinimaxElagator elagator) {
			this.node = node;
			this.elagator = elagator;
			start();
		}
		
		@Override
		public void run() {
			node.minimax(0, elagator);
			done();
		}
		
		private void done() {
			this.done = true;
		}
		
		public boolean isDone() {
			return done;
		}
		
	}
	
	
	public ArrayList<MinimaxNode> getBestChilds(MinimaxNode current) {
		MinimaxNode [] childs = current.childs();
		ArrayList<MinimaxNode> coups = new ArrayList<MinimaxNode>();
		double mm_best = childs[0].lastMinimax();
		
		if(current.player()) {
			for(int i=1;i<childs.length;i++) {
				double mm = childs[i].lastMinimax();
				if(mm > mm_best) {
					mm_best = mm;
					coups = new ArrayList<MinimaxNode>();
					coups.add(childs[i]);
				} else if(mm == mm_best) {
					coups.add(childs[i]);
				}
			}
		} else {
			for(int i=1;i<childs.length;i++) {
				double mm = childs[i].lastMinimax();
				if(mm < mm_best) {
					mm_best = mm;
					coups = new ArrayList<MinimaxNode>();
					coups.add(childs[i]);
				} else if(mm == mm_best) {
					coups.add(childs[i]);
				}
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
