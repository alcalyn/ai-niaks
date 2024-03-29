package views;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Coup;
import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Pion;
import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayerSocket;
import controllers.MenuButton;

public class NiakMenu extends JMenuBar implements Observer {
	
	private static final long serialVersionUID = 775631860645850544L;
	
	
	private static final int taille_plateau_min = 1;
	private static final int taille_plateau_max = 6;
	
	
	private Niaks niaks;
	private NiaksFrame niaks_frame;
	
	
	private JMenu		partie;
	private JMenuItem		lancer_partie;
	private JMenuItem		nouvelle_partie;
	private JMenuItem		recommencer;
	private JMenu			taille_plateau;
	private JCheckBoxMenuItem []	tailles = null;
	
	private JMenu		niakwork;
	private JMenuItem		connect;
	private JMenuItem		disconnect;
	private JMenuItem		search_local;
	private JMenuItem		connect_to;
	private JMenu			found_hosts;
	private JMenuItem[]		hosts;
	
	private JMenu		projet;
	private JMenuItem		show_minimax_view;
	
	private JMenu		about;
	private JMenuItem		project;
	
	
	
	public NiakMenu(Niaks niaks, NiaksFrame niaks_frame) {
		this.niaks = niaks;
		this.niaks_frame = niaks_frame;
		
		add(menuPartie());
		add(menuNiakwork());
		add(menuProjet());
		add(menuAbout());
		
		updateEtat(niaks.getEtat());
		updateNiakwork(niaks.isNiakworkEnabled());
	}
	
	

	private JMenu menuPartie() {
		partie = new JMenu("Partie");
		lancer_partie = new JMenuItem("Lancer la partie");
		taille_plateau = new JMenu("Taille du plateau");
		nouvelle_partie = new JMenuItem("Nouvelle partie");
		recommencer = new JMenuItem("Recommencer cette partie");
		
		lancer_partie.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.LANCER_PARTIE));
		nouvelle_partie.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.NEW_GAME));
		recommencer.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.RESTART_GAME));
		
		partie.add(lancer_partie);
		partie.add(taille_plateau);
		partie.addSeparator();
		partie.add(nouvelle_partie);
		partie.add(recommencer);
		
		return partie;
	}
	
	private JMenu menuNiakwork() {
		niakwork = new JMenu("Niakwork");
		
		connect = new JMenuItem("Se connecter");
		connect.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.CONNECT));
		niakwork.add(connect);
		
		disconnect = new JMenuItem("Se d�connecter");
		disconnect.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.DISCONNECT));
		niakwork.add(disconnect);
		
		niakwork.addSeparator();
		
		search_local = new JMenuItem("Rechercher sur le r�seau local");
		search_local.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.SEARCH_LOCAL));
		niakwork.add(search_local);
		
		connect_to = new JMenuItem("Se connecter � ...");
		connect_to.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.CONNECT_TO));
		niakwork.add(connect_to);
		
		niakwork.addSeparator();
		
		found_hosts = new JMenu("H�tes trouv�s");
		niakwork.add(found_hosts);
		
		
		return niakwork;
	}
	
	private JMenu menuProjet() {
		projet = new JMenu("Projet L3");
		
		show_minimax_view = new JMenuItem("Ouvrir une vue Minimax");
		show_minimax_view.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.SHOW_MINIMAX_VIEW));
		projet.add(show_minimax_view);
		
		return projet;
	}
	
	private JMenu menuAbout() {
		about = new JMenu("?");
		project = new JMenuItem("A propos du projet");
		
		project.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.SHOW_ABOUT_PROJECT));
		
		about.add(project);
		
		return about;
	}
	
	
	private void refreshTaillePlateau() {
		if(niaks.getEtat() == Niaks.PREPARATION) {
			if(tailles == null) {
				tailles = new JCheckBoxMenuItem[taille_plateau_max - taille_plateau_min + 1];
				
				int k = 0;
				for(int i=taille_plateau_min;i<=taille_plateau_max;i++) {
					JCheckBoxMenuItem taille = new JCheckBoxMenuItem(Integer.toString(i));
					taille.addActionListener(new MenuButton(niaks, niaks_frame, MenuButton.SELECT_TAILLE_PLATEAU, new Object[] {i}));
					taille_plateau.add(taille);
					tailles[k++] = taille;
				}
			}
			
			for(int i=taille_plateau_min;i<=taille_plateau_max;i++) {
				tailles[i - taille_plateau_min].setSelected(niaks.getPartiePreparator().getPlateauSize() == i);
			}
			
			taille_plateau.setEnabled(true);
		} else {
			taille_plateau.setEnabled(false);
		}
	}

	
	private void refreshHostCount() {
		String s = "";
		NiakworkHostSocket [] hosts_socket = niaks.getNiakwork().getHostsFound();
		int n = hosts_socket.length;
		
		if(n > 0) {
			s = " ("+n+")";
		}
		
		niakwork.setText("Niakwork"+s);
		found_hosts.setText("H�tes trouv�s"+s);
		found_hosts.setEnabled(n > 0);
		
		hosts = new JMenuItem[n];
		found_hosts.removeAll();
		int i = 0;
		for (NiakworkHostSocket host_socket : hosts_socket) {
			JMenuItem menu_item = new JMenuItem(host_socket.getPseudo()+" @ "+host_socket.getFullAdress());
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
		taille_plateau.setEnabled(etat_partie == Niaks.PREPARATION);
		
		nouvelle_partie.setEnabled(etat_partie == Niaks.PARTIE);
		recommencer.setEnabled(etat_partie == Niaks.PARTIE);
		refreshTaillePlateau();
		
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
		refreshTaillePlateau();
	}



	@Override
	public void updateJoueurs(Joueur[] joueurs) {
	}



	@Override
	public void updatePions(Pion[][] pions, Coup coup) {
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



	@Override
	public void updateGameFinished() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
	
	
}
