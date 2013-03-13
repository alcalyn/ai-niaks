package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.CoupEmitter;
import model.CoupListener;
import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;



public class NiaksFrame extends JFrame implements Observer, CoupEmitter {
	
	private static final long serialVersionUID = 7409878114591059470L;
	
	
	private JPanel card_pseudo = null;
	private JPanel card_prepare = null;
	private JPanel card_game = null;
	
	private CardLayout card_layout = new CardLayout();
	
	
	public NiaksFrame() {
		super();
		initFrame();
		
		//add(new CardPseudo(), "pseudo");
	}
	
	
	private void initFrame() {
		setTitle("Niaks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new CardLayout());
		setVisible(true);
	}
	
	
	
	public void startPartie(Partie partie) {
		card_game = new CardGame(partie);
		partie.addObserver((Observer) card_game);
		
		add(card_game, "game");
	}
	
	
	
	@Override
	public void addCoupListener(CoupListener coup_listener) {
	}
	
	@Override
	public void updatePions(Pion[][] pions) {
	}

	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		setTitle("Niaks - (A "+joueur.getPseudo()+" de jouer)");
		repaint();
	}
	
	
}
