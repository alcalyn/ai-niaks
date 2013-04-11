package model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import strategies.BackFirstStrategy;
import strategies.SimpleStrategy;

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
	
	
	public static HashMap<String, Strategy> strategies = null;
	
	public static HashMap<String, Strategy> getStrategiesAndDifficult() {
		if(strategies == null) {
			strategies = new HashMap<String, Strategy>();
			
			strategies.put("Facile", new SimpleStrategy());
			strategies.put("Moyen", new BackFirstStrategy());
		}
		
		return strategies;
	}
	
	public static Strategy getStrategyByDifficult(String s) {
		return getStrategiesAndDifficult().get(s);
	}
	
	public static String getDifficultByStrategy(Strategy s) {
		Set<Entry<String, Strategy>> a = getStrategiesAndDifficult().entrySet();
		for (Entry<String, Strategy> entry : a) {
			if(entry.getValue().getClass().getSimpleName().equals(s.getClass().getSimpleName())) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	
}
