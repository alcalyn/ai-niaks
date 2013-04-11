package views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import model.Coup;
import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;
import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayerSocket;

public class JoueursPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 4353656578680780896L;
	
	
	private JoueurPanel [] joueur_panel;
	
	
	public JoueursPanel(Partie partie) {
		super();
		partie.getNiaks().addObserver(this);
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
		setBackground(new Color(0x88F6F6F6, true));
	}
	
	
	@Override
	public void updatePions(Pion[][] pions, Coup coup) {
	}

	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		for (JoueurPanel p : joueur_panel) {
			p.updateCurrentPlayer(joueur);
		}
	}


	@Override
	public void updateEtat(int etat_partie) {
	}


	@Override
	public void updateTaillePlateau(int taille) {
	}


	@Override
	public void updateJoueurs(Joueur[] joueurs) {
	}


	@Override
	public void updateProfil(String pseudo) {
	}


	@Override
	public void updateNiakwork(boolean isEnabled) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket,
			String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket,
			String reason) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
		// TODO Auto-generated method stub
		
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
