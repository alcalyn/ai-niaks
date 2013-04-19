package appli;

import model.Niaks;
import model.Ordinateur;
import strategies.BackFirstStrategy;
import views.NiaksFrame;
import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;


public class Appli {


	public static void main(String[] args) {
		System.out.println("Starting appli");
		
		Niaks niaks = new Niaks();
		
		new NiaksFrame(niaks);
		//new ConsoleView(niaks);
		
		
		boolean lancer_direct = false;
		
		if(lancer_direct) {
			try {
				niaks.setProfil("Ju");
				niaks.startPreparation();
				niaks.getPartiePreparator().addJoueur(new Ordinateur(new BackFirstStrategy()));
				niaks.getPartiePreparator().setPlateauSize(2);
				
				niaks.startPartie();
			} catch (ProfilNotSetNiaksException e) {
				e.printStackTrace();
			} catch (PartieNotReadyToStartNiaksException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
