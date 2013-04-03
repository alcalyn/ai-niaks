package minimax;

public class MinimaxStats {
	
	
	
	
	public static int node_count;
	public static int start_depth;
	public static int max_depth;
	public static int elagage_count;
	public static int horizon_size;
	
	
	public static void init() {
		node_count = 0;
		start_depth = 0;
		elagage_count = 0;
		horizon_size = 0;
	}
	
	public static void trace() {
		System.out.println("===== MinimaxStats =====");
		System.out.println(node_count+" noeuds explorés.");
		System.out.println("max depth = "+max_depth);
		System.out.println("nb d'elagage = "+elagage_count);
		System.out.println("taille de l'horizon = "+horizon_size);
	}
	
	public static void depth(int depth) {
		max_depth = Math.max(depth, start_depth);
	}
	
}
