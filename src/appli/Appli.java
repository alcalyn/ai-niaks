package appli;

import model.Niaks;
import model.Ordinateur;
import views.NiaksFrame;
import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;


public class Appli {


	public static void main(String[] args) {
		System.out.println("Starting appli");
		
		Niaks niaks = new Niaks();
		
		NiaksFrame niaks_frame = new NiaksFrame(niaks);
		
		
		boolean lancer_direct = true;
		
		if(lancer_direct) {
			try {
				niaks.setProfil("Ju");
				niaks.startPreparation();
				niaks.getPartiePreparator().removeAllJoueur();
				niaks.getPartiePreparator().addJoueur(new Ordinateur(1.0));
				niaks.getPartiePreparator().addJoueur(new Ordinateur(1.0));
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
