package niakwork;

import java.net.Socket;

import model.Coup;
import model.Joueur;



public class NiakworkPlayer extends Joueur {
	
	
	
	

	public NiakworkPlayer(String pseudo, Socket socket) {
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
