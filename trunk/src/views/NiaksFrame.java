package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayerSocket;

import model.Coup;
import model.Humain;
import model.Joueur;
import model.Niaks;
import model.Observer;
import model.Partie;
import model.PartiePreparator;
import model.Pion;
import model.ProfilManager;
import model.Res;
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
		
		setVisible(true);
	}
	
	
	private void initFrame() {
		String pseudo = niaks.getProfil();
		setTitle(pseudo == null ? "Niaks" : "Niaks - "+pseudo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());
		setIconImage(Res.getImage("niaks-icon.png"));
	}
	
	private void initNiakMenu() {
		niak_menu = new NiakMenu(niaks, this);
		niaks.addObserver(niak_menu);
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
	public void updatePions(Pion[][] pions, Coup coup) {
		try {
			String pseudo = niaks.getProfil();
			String sTours = Integer.toString(niaks.getPartie().getPlateau().getTour());
			setTitle(pseudo == null ? "Niaks" : "Niaks - "+pseudo+" (Tour "+sTours+")");
		} catch(NullPointerException e) {
			String pseudo = niaks.getProfil();
			setTitle(pseudo == null ? "Niaks" : "Niaks - "+pseudo);
		}
	}

	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		if(joueur instanceof Humain) {
			// permet de faire clignoter l'icone dans
			// la barre windows quand c'est à nous de jouer.
			// déprécié, tampis.
			show();
		}
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


	@Override
	public void updateNiakwork(boolean isEnabled) {
	}


	@Override
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
		int response = JOptionPane.showConfirmDialog(
			this,
			"'"+pseudo+"' veut se connecter",
			"Niakwork",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE
		);
		
		if(response == JOptionPane.YES_OPTION) {
			niaks.niakworkAcceptClient(npsocket, pseudo);
		} else {
			niaks.niakworkDenyClient(npsocket);
		}
	}


	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket,
			String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket, String reason) {
		JOptionPane.showMessageDialog(this, "L'hôte vous a refusé", "Niakwork", JOptionPane.ERROR_MESSAGE);
	}


	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
		JOptionPane.showMessageDialog(this, "L'hôte vous a accépté", "Niakwork", JOptionPane.INFORMATION_MESSAGE);
	}


	@Override
	public void updateJoueurWon(Joueur joueur) {
		JOptionPane.showMessageDialog(
				this,
				joueur.getPseudo()+" a gagné",
				"Partie",
				JOptionPane.INFORMATION_MESSAGE
		);
	}


	@Override
	public void updateGameFinished() {
		JOptionPane.showMessageDialog(
				this,
				"La partie est terminée",
				"Partie",
				JOptionPane.INFORMATION_MESSAGE
		);
	}


	
	
}
