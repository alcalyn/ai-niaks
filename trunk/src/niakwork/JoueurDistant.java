package niakwork;

import java.net.Socket;

import model.Coup;
import model.CoupListener;
import model.IllegalMoveNiaksException;
import model.Joueur;



public class JoueurDistant extends Joueur {
	
	
	
	

	public JoueurDistant(String pseudo, Socket socket) {
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
