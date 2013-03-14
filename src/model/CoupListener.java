package model;

import java.util.EventListener;

import exceptions.IllegalMoveNiaksException;

public interface CoupListener extends EventListener {
	
	
	public void coupPlayed(Coup coup) throws IllegalMoveNiaksException;

	public void purge();
	
	
}
