package views;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Plateau;

public class MinimaxNodeView extends JPanel {
	
	private static final long serialVersionUID = 1792379591459983148L;
	
	
	private PlateauMiniature miniature;
	private JLabel label_south;

	public MinimaxNodeView(final MinimaxView context, final Plateau plateau) {
		super();
		
		setLayout(new BorderLayout());
		
		miniature = new PlateauMiniature(plateau);
		
		label_south = new JLabel();
		write("Mmax = "+plateau.lastMinimax()+" | h = "+plateau.eval()+" | "+plateau.childs().length+" fils");
		
		add(miniature, BorderLayout.CENTER);
		add(label_south, BorderLayout.SOUTH);
		
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				context.updateProcessed(plateau, plateau.childs(), null);
			}
		});
	}
	
	
	private void write(String s) {
		label_south.setText(s);
		repaint();
	}
	

}
