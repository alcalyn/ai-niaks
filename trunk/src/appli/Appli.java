package appli;

import views.NiaksFrame;
import model.Humain;
import model.Joueur;
import model.Model;
import model.Observer;
import model.Ordinateur;
import model.Partie;

public class Appli {

	//hello world

	public static void main(String[] args) {

		int taille_plateau = 4;
		
		Joueur [] joueurs = new Joueur[] {
			new Humain("Vous"),
			new Ordinateur(0.5)
		};
		
		Model model = new Partie(joueurs, taille_plateau);

		Observer view = new NiaksFrame((Partie) model);
		
		model.addObserver(view);
	}

}
