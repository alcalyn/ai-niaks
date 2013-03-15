package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Partie;
import model.PartiePreparator;
import model.Pion;
import model.ProfilManager;
import controllers.ExceptionCatcher;
import exceptions.NiaksException;
import exceptions.ProfilNotSetNiaksException;



public class NiaksFrame extends JFrame implements Observer, ExceptionCatcher {
	
	private static final long serialVersionUID = 7409878114591059470L;
	
	private Niaks niaks;
	
	private JPanel card_pseudo = null;
	private JPanel card_prepare = null;
	private JPanel card_game = null;
	
	private NiakMenu niak_menu;
	
	
	public NiaksFrame(Niaks niaks) {
		super();
		this.niaks = niaks;
		initFrame();

		initNiakMenu();
		
		niaks.addObserver(this);
		
		checkForProfil();
	}
	
	
	private void initFrame() {
		String pseudo = niaks.getProfil();
		setTitle(pseudo == null ? "Niaks" : "Niaks - "+pseudo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());
		setVisible(true);
	}
	
	private void initNiakMenu() {
		niak_menu = new NiakMenu(niaks, this);
		add(niak_menu, BorderLayout.NORTH);
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
		
		clean();
		add(card_pseudo, BorderLayout.CENTER);
		validate();
	}
	
	public void startPreparation(PartiePreparator partie_preparator) {
		if(card_pseudo != null) {
			card_pseudo = null;
		}
		
		card_prepare = new CardPrepare(partie_preparator);
		niaks.addObserver((Observer) card_prepare);
		
		clean();
		add(card_prepare, BorderLayout.CENTER);
	}
	
	public void startPartie(Partie partie) {
		if(card_prepare != null) {
			niaks.removeObserver((Observer) card_prepare);
			card_prepare = null;
		}
		
		card_game = new CardGame(partie);
		niaks.addObserver((Observer) card_game);
		
		clean();
		add(card_game);
	}
	
	
	private void clean() {
		getContentPane().removeAll();
		add(niak_menu, BorderLayout.NORTH);
	}
	
	
	
	@Override
	public void updatePions(Pion[][] pions) {
	}

	@Override
	public void updateCurrentPlayer(Joueur joueur) {
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


	@Override
	public void updateProfil(String pseudo) {
		setTitle(pseudo == null ? "Niaks" : "Niaks - "+pseudo);
	}


	@Override
	public void catchException(NiaksException e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	}


	
	
}
