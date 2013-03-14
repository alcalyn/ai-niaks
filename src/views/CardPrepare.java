package views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Joueur;
import model.Observer;
import model.PartiePreparator;
import model.Pion;

public class CardPrepare extends JPanel implements Observer {
	
	private static final long serialVersionUID = -536690658232712587L;
	
	
	
	private PartiePreparator partie_preparator;
	

	public CardPrepare(PartiePreparator partie_preparator) {
		super();
		
		this.partie_preparator = partie_preparator;
		
		add(new JLabel(partie_preparator.getHost().getPseudo()));
	}


	
	@Override
	public void updateEtat(int etat_partie) {
	}


	@Override
	public void updateTaillePlateau(int taille) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateJoueurs(Joueur[] joueurs) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updatePions(Pion[][] pions) {
	}


	@Override
	public void updateCurrentPlayer(Joueur joueur) {
	}

}
