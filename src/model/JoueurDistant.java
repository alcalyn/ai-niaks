package model;

import exceptions.IllegalMoveNiaksException;



public class JoueurDistant extends Joueur implements CoupListener {
	
	
	private Coup coup_recu = null;
	

	public JoueurDistant(String pseudo, String ip) {
		super(pseudo);
	}

	@Override
	public Coup jouerCoup() {
		return coup_recu;
	}

	public void coupPlayed(Coup coup) throws IllegalMoveNiaksException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean playsInstantly() {
		return false;
	}

	public void purge() {
		coup_recu = null;
	}

}
