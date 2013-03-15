package appli;

import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;
import model.Humain;
import model.Niaks;
import model.Ordinateur;
import views.NiaksFrame;


public class Appli {

	//hello world

	public static void main(String[] args) {
		
		
		Niaks niaks = new Niaks();
		
		NiaksFrame niaks_frame = new NiaksFrame(niaks);
		
		
		boolean lancer_direct = false;
		
		if(lancer_direct) {
			try {
				niaks.setProfil("Roger");
				niaks.startPreparation();
				niaks.enableNiakwork();
				niaks.getPartiePreparator().addJoueur(new Humain("Alfred"));
				
				niaks.startPartie();
			} catch (ProfilNotSetNiaksException e) {
				e.printStackTrace();
			} catch (PartieNotReadyToStartNiaksException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
