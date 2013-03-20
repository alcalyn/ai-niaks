package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import niakwork.NiakworkHostSocket;
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
	private JMenuItem		search_local;
	private JMenuItem		connect_to;
	private JMenu			found_hosts;
	private JMenuItem[]		hosts;
	
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
		
		search_local = new JMenuItem("Rechercher sur le réseau local");
		search_local.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.SEARCH_LOCAL));
		niakwork.add(search_local);
		
		connect_to = new JMenuItem("Se connecter à ...");
		connect_to.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.CONNECT_TO));
		niakwork.add(connect_to);
		
		niakwork.addSeparator();
		
		found_hosts = new JMenu("Hôtes trouvés");
		niakwork.add(found_hosts);
		
		
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

	
	private void refreshHostCount() {
		String s = "";
		NiakworkHostSocket [] hosts_socket = niaks.getNiakwork().getHostsFound();
		int n = hosts_socket.length;
		
		if(n > 0) {
			s = " ("+n+")";
		}
		
		niakwork.setText("Niakwork"+s);
		found_hosts.setText("Hôtes trouvés"+s);
		found_hosts.setEnabled(n > 0);
		
		hosts = new JMenuItem[n];
		found_hosts.removeAll();
		int i = 0;
		for (NiakworkHostSocket host_socket : hosts_socket) {
			JMenuItem menu_item = new JMenuItem(host_socket.getFullAdress());
			hosts[i++] = menu_item;
			menu_item.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.JOIN_HOST, new Object[]{host_socket}));
			found_hosts.add(menu_item);
		}
		
		validate();
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
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
	}



	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket, String pseudo) {
		refreshHostCount();
	}



	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket,
			String reason) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
		lancer_partie.setEnabled(false);
	}



	@Override
	public void updateJoueurWon(Joueur joueur) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
	
	
}
