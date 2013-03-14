package model;

import exceptions.IllegalMoveNiaksException;



public class Humain extends Joueur implements CoupListener {
	
	
	private Coup coup_recu = null;
	

	public Humain(String pseudo) {
		super(pseudo);
	}
	

	@Override
	public Coup jouerCoup() {
		Coup c = coup_recu;
		coup_recu = null;
		return c;
	}

	public void coupPlayed(Coup coup) throws IllegalMoveNiaksException {
		if(coup.getJoueur() == this) {
			coup_recu = coup;
			getPartie().notifyCoupPlayed(this);
		}
	}


	@Override
	public boolean playsInstantly() {
		return false;
	}


	public void purge() {
		coup_recu = null;
	}
	

}
