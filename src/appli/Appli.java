package appli;

import model.Humain;
import model.Joueur;
import model.Ordinateur;
import model.Partie;
import views.NiaksFrame;

public class Appli {

	//hello world

	public static void main(String[] args) {

		int taille_plateau = 4;
		
		Joueur [] joueurs = new Joueur[] {
			new Humain("Marcel"),
			new Ordinateur(1),
			new Humain("Julien"),
			new Humain("Aragorn fils d'Arathorn, roi des Terres du Milieu"),
		};
		
		
		Partie partie = new Partie(joueurs, taille_plateau);

		NiaksFrame view = new NiaksFrame(partie);
		
		partie.addObserver(view);
		
		
		for (Joueur joueur : joueurs) {
			if(joueur instanceof Humain) {
				view.addCoupListener((Humain) joueur);
			}
		}
		
		partie.enableNiakwork();
		partie.getNiakwork().searchHost();
	}

}
