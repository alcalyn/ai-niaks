package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Pion;
import controllers.LancerPartie;

public class NiakMenu extends JMenuBar implements Observer {
	
	private static final long serialVersionUID = 775631860645850544L;
	
	
	private Niaks niaks;
	private NiaksFrame niaks_frame;
	
	public NiakMenu(Niaks niaks, NiaksFrame niaks_frame) {
		this.niaks = niaks;
		this.niaks_frame = niaks_frame;
		
		add(menuPartie());
		add(menuJoueurs());
		add(menuAbout());
	}
	
	

	private JMenu menuPartie() {
		JMenu menu = new JMenu("Partie");
		
		JMenuItem lancer_partie = new JMenuItem("Lancer la partie");
		lancer_partie.addActionListener(new LancerPartie(niaks, niaks_frame));
		menu.add(lancer_partie);
		
		return menu;
	}
	
	private JMenu menuJoueurs() {
		JMenu menu = new JMenu("Joueurs");
		
		return menu;
	}
	
	private JMenu menuAbout() {
		JMenu menu = new JMenu("?");
		
		return menu;
	}

	
	
	


	@Override
	public void updateProfil(String pseudo) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateEtat(int etat_partie) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateTaillePlateau(int taille) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateJoueurs(Joueur[] joueurs) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updatePions(Pion[][] pions) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
}
