package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.CoupEmitter;
import model.CoupListener;
import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;



public class NiaksFrame extends JFrame implements Observer, CoupEmitter {
	
	private static final long serialVersionUID = 7409878114591059470L;
	
	
	
	private Partie partie;
	
	private PlateauPanel plateau_panel;
	private JoueursPanel joueurs_panel;
	
	
	public NiaksFrame(Partie partie) {
		super();
		this.partie = partie;
		initFrame();
		
		initPlateauPanel();
		initJoueursPanel();
		
		partie.addObserver(plateau_panel);
		partie.addObserver(joueurs_panel);
		
		Joueur j = partie.getJoueur();
		updateCurrentPlayer(j);
		joueurs_panel.updateCurrentPlayer(j);
	}

	private void initFrame() {
		setTitle("Niaks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());
		setVisible(true);
	}
	
	private void initPlateauPanel() {
		plateau_panel = new PlateauPanel(partie);
		add(plateau_panel, BorderLayout.CENTER);
	}
	
	private void initJoueursPanel() {
		joueurs_panel = new JoueursPanel(partie);
		add(joueurs_panel, BorderLayout.EAST);
	}
	
	
	public void addCoupListener(CoupListener coup_listener) {
		plateau_panel.addCoupListener(coup_listener);
	}
	

	public void updatePions(Pion[][] pions) {
		repaint();
	}


	public void updateCurrentPlayer(Joueur joueur) {
		setTitle("Niaks - (A "+joueur.getPseudo()+" de jouer)");
		repaint();
	}
	
	
}
