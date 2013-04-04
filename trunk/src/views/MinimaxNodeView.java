package views;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Plateau;

public class MinimaxNodeView extends JPanel {
	
	private static final long serialVersionUID = 1792379591459983148L;
	
	
	private PlateauMiniature miniature;
	private JLabel label_south;

	public MinimaxNodeView(Plateau plateau) {
		super();
		
		setLayout(new BorderLayout());
		
		miniature = new PlateauMiniature(plateau);
		
		label_south = new JLabel();
		write("Mmax = "+plateau.lastMinimax()+" | h = "+plateau.eval());
		
		add(miniature, BorderLayout.CENTER);
		add(label_south, BorderLayout.SOUTH);
	}
	
	
	private void write(String s) {
		label_south.setText(s);
		repaint();
	}
	

}
