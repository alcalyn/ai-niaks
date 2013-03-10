package appli;

import model.Humain;
import model.Joueur;
import model.Model;
import model.Observer;
import model.Ordinateur;
import model.Partie;
import views.NiaksFrame;

public class Appli {

	//hello world

	public static void main(String[] args) {

		int taille_plateau = 4;
		
		Joueur [] joueurs = new Joueur[] {
			new Humain("Marcel"),
			new Ordinateur(0.5),
			new Humain("Julien"),
			new Ordinateur(0.5),
			new Humain("Aragorn fils d'Arathorn, roi des Terres du Milieu"),
			new Ordinateur(0.5),
		};
		
		Model model = new Partie(joueurs, taille_plateau);

		Observer view = new NiaksFrame((Partie) model);
		
		model.addObserver(view);
		
	}

}
