package model;

import strategies.BackFirstStrategy;

public class Strategies {
	
	private static Strategy default_strategy = null;
	
	public static Strategy getDefaultStrategy() {
		if(default_strategy == null) {
			default_strategy = new BackFirstStrategy();
		}
		
		return default_strategy;
	}
	
	
	public static final int
		MAX_SCORE =  100000,
		MIN_SCORE = -100000;
	
	
	
}
