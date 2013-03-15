package niakwork;

import model.Coup;
import model.Joueur;



public class NiakworkPlayer extends Joueur {
	
	
	
	

	public NiakworkPlayer(String pseudo, NiakworkPlayerSocket npsocket) {
		super(pseudo);
	}

	@Override
	public Coup jouerCoup() {
		return null;
	}

	@Override
	public boolean playsInstantly() {
		return false;
	}

}
