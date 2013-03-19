package appli;

import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;
import model.Coords;
import model.Coords3;
import model.Humain;
import model.Niaks;
import model.Ordinateur;
import views.NiaksFrame;


public class Appli {

	
	public static void test(int x, int y) {
		Coords3 c3 = new Coords(x, y).toCoords3();
		int distance = Math.abs(c3.x) + Math.abs(c3.y) + Math.abs(c3.z);
		System.out.println("dist "+x+" "+y+" ==> "+distance);
	}

	public static void main(String[] args) {
		
		Niaks niaks = new Niaks();
		
		NiaksFrame niaks_frame = new NiaksFrame(niaks);
		
		
		boolean lancer_direct = false;
		
		if(lancer_direct) {
			try {
				niaks.setProfil("Roger");
				niaks.startPreparation();
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
