package model;

import java.util.EventListener;

public interface CoupListener extends EventListener {
	
	
	public void coupPlayed(Coup coup) throws IllegalMoveNiaksException;

	public void purge();
	
	
}
