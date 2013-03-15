package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.CoupEmitter;
import model.CoupListener;
import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;

public class CardGame extends JPanel implements Observer, CoupEmitter {
	
	private static final long serialVersionUID = -6432097786904712576L;
	
	

	private Partie partie;
	
	private PlateauPanel plateau_panel;
	private JoueursPanel joueurs_panel;
	
	
	
	public CardGame(Partie partie) {
		super();
		this.partie = partie;
		setLayout(new BorderLayout());
		
		initPlateauPanel();
		initJoueursPanel();
		
		partie.getNiaks().addObserver(plateau_panel);
		partie.getNiaks().addObserver(joueurs_panel);
		
		Joueur j = partie.getJoueur();
		updateCurrentPlayer(j);
		joueurs_panel.updateCurrentPlayer(j);
	}
	
	
	private void initPlateauPanel() {
		plateau_panel = new PlateauPanel(partie);
		add(plateau_panel, BorderLayout.CENTER);
	}
	
	private void initJoueursPanel() {
		joueurs_panel = new JoueursPanel(partie);
		add(joueurs_panel, BorderLayout.EAST);
	}
	
	


	@Override
	public void addCoupListener(CoupListener coup_listener) {
		plateau_panel.addCoupListener(coup_listener);
	}


	@Override
	public void updatePions(Pion[][] pions) {
		repaint();
	}


	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		repaint();
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

}
