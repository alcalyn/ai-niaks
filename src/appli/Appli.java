package appli;

import views.NiaksFrame;
import model.Partie;

public class Appli {

	
	
	public static void main(String[] args) {
		
		int taille_plateau = 4;
		int nb_joueur = 2;
		
		Partie partie = new Partie(nb_joueur, taille_plateau);
		
		NiaksFrame frame = new NiaksFrame(partie);
	}

}
