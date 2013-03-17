package niakwork;

import model.Coup;
import model.Joueur;



public class NiakworkPlayer extends Joueur {
	
	private static final long serialVersionUID = -2703369154033979320L;
	
	
	private NiakworkPlayerSocket npsocket;
	

	public NiakworkPlayer(String pseudo, NiakworkPlayerSocket npsocket) {
		super(pseudo);
		this.npsocket = npsocket;
	}
	
	public NiakworkPlayerSocket getNiakworkPlayerSocket() {
		return npsocket;
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
