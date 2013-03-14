package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import model.Niaks;

public class NiakMenu extends JMenuBar {
	
	private static final long serialVersionUID = 775631860645850544L;
	
	
	private Niaks niaks;
	
	
	public NiakMenu(Niaks niaks) {
		this.niaks = niaks;
		
		add(menuPartie());
		add(menuJoueurs());
		add(menuAbout());
	}
	
	

	private JMenu menuPartie() {
		JMenu menu = new JMenu("Partie");
		
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
	
	
	
	
	
	
}
