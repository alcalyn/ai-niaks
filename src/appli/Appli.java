package appli;

import views.NiaksFrame;
import model.Model;
import model.Observer;
import model.Partie;

public class Appli {

	//hello world

	public static void main(String[] args) {

		int taille_plateau = 4;
		int nb_joueur = 2;
		
		Model model = new Partie(nb_joueur, taille_plateau);

		Observer view = new NiaksFrame((Partie) model);
		
		model.addObserver(view);
	}

}
