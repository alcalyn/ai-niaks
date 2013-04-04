package views;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Joueur;
import model.Partie;
import model.Plateau;

public class MinimaxNodeView extends JPanel {
	
	private static final long serialVersionUID = 1792379591459983148L;
	
	
	private Plateau plateau;
	
	private PlateauMiniature miniature;
	private JLabel label_south;

	public MinimaxNodeView(Plateau plateau) {
		super();
		this.plateau = plateau;
		
		setLayout(new BorderLayout());
		
		miniature = new PlateauMiniature(plateau);
		
		Partie partie = plateau.getPartie();
		Joueur [] js = partie.getJoueurs();
		write("Mmax = "+plateau.lastMinimax()+" | h = "+plateau.eval()+" | "+partie.hasWon(js[0])+" "+partie.hasWon(js[1]));
		
		add(miniature, BorderLayout.CENTER);
		add(label_south, BorderLayout.SOUTH);
	}
	
	
	private void write(String s) {
		label_south = new JLabel(s);
	}
	

}
