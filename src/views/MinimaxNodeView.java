package views;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
		label_south = new JLabel("Minimax = "+plateau.lastMinimax()+" | Eval = "+plateau.eval());
		
		add(miniature, BorderLayout.CENTER);
		add(label_south, BorderLayout.SOUTH);
	}

}
