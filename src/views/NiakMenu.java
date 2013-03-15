package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import niakwork.NiakworkPlayerSocket;

import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Pion;
import controllers.MenuButton;

public class NiakMenu extends JMenuBar implements Observer {
	
	private static final long serialVersionUID = 775631860645850544L;
	
	
	private Niaks niaks;
	private NiaksFrame niaks_frame;
	
	
	private JMenu		partie;
	private JMenuItem		lancer_partie;
	
	private JMenu		joueurs;
	
	private JMenu		niakwork;
	private JMenuItem		connect;
	private JMenuItem		disconnect;
	private JMenu			found_hosts;
	private JMenuItem			no_host_found;
	
	private JMenu		projet;
	
	private JMenu		about;
	
	
	public NiakMenu(Niaks niaks, NiaksFrame niaks_frame) {
		this.niaks = niaks;
		this.niaks_frame = niaks_frame;
		
		add(menuPartie());
		add(menuJoueurs());
		add(menuNiakwork());
		add(menuProjet());
		add(menuAbout());
		
		updateNiakwork(niaks.isNiakworkEnabled());
	}
	
	

	private JMenu menuPartie() {
		partie = new JMenu("Partie");
		
		lancer_partie = new JMenuItem("Lancer la partie");
		lancer_partie.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.LANCER_PARTIE));
		partie.add(lancer_partie);
		
		return partie;
	}
	
	private JMenu menuJoueurs() {
		joueurs = new JMenu("Joueurs");
		
		return joueurs;
	}
	
	private JMenu menuNiakwork() {
		niakwork = new JMenu("Niakwork");
		
		connect = new JMenuItem("Se connecter");
		connect.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.CONNECT));
		niakwork.add(connect);
		
		disconnect = new JMenuItem("Se déconnecter");
		disconnect.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.DISCONNECT));
		niakwork.add(disconnect);
		
		niakwork.addSeparator();
		
		found_hosts = new JMenu("Hôtes trouvés");
		niakwork.add(found_hosts);
		
		no_host_found = new JMenuItem("aucun hôte trouvé...");
		no_host_found.setEnabled(false);
		found_hosts.add(no_host_found);
		
		return niakwork;
	}
	
	private JMenu menuProjet() {
		projet = new JMenu("Projet L3");
		
		return projet;
	}
	
	private JMenu menuAbout() {
		about = new JMenu("?");
		
		return about;
	}

	
	
	


	@Override
	public void updateProfil(String pseudo) {
	}



	@Override
	public void updateEtat(int etat_partie) {
		lancer_partie.setEnabled(etat_partie == Niaks.PREPARATION);
		
		/*
		switch (etat_partie) {
			case Niaks.PSEUDO:
				break;
				
			case Niaks.PREPARATION:
				break;
				
			case Niaks.PARTIE:
				break;
	
			default:
				break;
		}
		*/
	}



	@Override
	public void updateTaillePlateau(int taille) {
	}



	@Override
	public void updateJoueurs(Joueur[] joueurs) {
	}



	@Override
	public void updatePions(Pion[][] pions) {
	}



	@Override
	public void updateCurrentPlayer(Joueur joueur) {
	}



	@Override
	public void updateNiakwork(boolean isEnabled) {
		connect.setEnabled(!isEnabled);
		disconnect.setEnabled(isEnabled);
	}



	@Override
	public void updateNiakworkClientFound(NiakworkPlayerSocket npsocket) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
}
