package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.CoupEmitter;
import model.CoupListener;
import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Partie;
import model.PartiePreparator;
import model.Pion;
import model.ProfilManager;
import model.ProfilNotSetNiaksException;



public class NiaksFrame extends JFrame implements Observer, CoupEmitter {
	
	private static final long serialVersionUID = 7409878114591059470L;
	
	private Niaks niaks;
	
	private JPanel card_pseudo = null;
	private JPanel card_prepare = null;
	private JPanel card_game = null;
	
	
	public NiaksFrame(Niaks niaks) {
		super();
		this.niaks = niaks;
		initFrame();
		
		checkForProfil();
	}
	
	
	private void initFrame() {
		setTitle("Niaks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());
		setVisible(true);
	}
	
	
	public void checkForProfil() {
		String pseudo = ProfilManager.getPseudo();
		
		if(pseudo == null) {
			promptPseudo();
		} else {
			niaks.setProfil(pseudo);
			
			try {
				niaks.startPreparation();
			} catch (ProfilNotSetNiaksException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void promptPseudo() {
		card_pseudo = new CardPseudo(niaks);
		
		add(card_pseudo);
		
		validate();
	}
	
	public void startPreparation(PartiePreparator partie_preparator) {
		card_prepare = new CardPrepare(partie_preparator);
		niaks.addObserver((Observer) card_prepare);
		
		if(card_pseudo != null) {
			remove(card_pseudo);
			card_pseudo = null;
		}
		
		add(card_prepare);
	}
	
	public void startPartie(Partie partie) {
		card_game = new CardGame(partie);
		niaks.addObserver((Observer) card_game);
		
		add(card_game);
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


	@Override
	public void updateEtat(int etat_partie) {
		switch (etat_partie) {
			case Niaks.PSEUDO:
				promptPseudo();
				break;
				
			case Niaks.PREPARATION:
				startPreparation(niaks.getPartiePreparator());
				break;
				
			case Niaks.PARTIE:
				startPartie(niaks.getPartie());
				break;

			default:
				break;
		}
		
		validate();
	}


	


	@Override
	public void updateTaillePlateau(int taille) {
	}


	@Override
	public void updateJoueurs(Joueur[] joueurs) {
	}
	
	
}
