package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;

public class JoueursPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 4353656578680780896L;
	
	
	private JoueurPanel [] joueur_panel;
	
	
	public JoueursPanel(Partie partie) {
		super();
		partie.addObserver(this);
		initPanel();
		
		joueur_panel = new JoueurPanel[partie.getNbJoueur()];
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 6, 3, 6);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		int k = 0;
		for (Joueur j : partie.getJoueurs()) {
			c.gridy = k;
			this.add(joueur_panel[k++] = new JoueurPanel(j), c);
		}
	}
	
	
	private void initPanel() {
		setLayout(new GridBagLayout());
	}
	
	

	public void updatePions(Pion[][] pions) {
	}


	public void updateCurrentPlayer(Joueur joueur) {
		for (JoueurPanel p : joueur_panel) {
			p.updateCurrentPlayer(joueur);
		}
	}
	
	
	

}
