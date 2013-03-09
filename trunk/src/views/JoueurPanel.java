package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Joueur;

public class JoueurPanel extends JPanel{
	
	private static final long serialVersionUID = 6501616382236225831L;
	
	
	private Joueur joueur;
	private boolean isCurrent = false;
	private JLabel label_pseudo;
	
	
	public JoueurPanel(Joueur joueur) {
		super();
		this.joueur = joueur;
		initPanel();
		
		label_pseudo = new JLabel(joueur.getPseudo());
		label_pseudo.setOpaque(true);
		label_pseudo.setBackground(Color.WHITE);
		label_pseudo.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		add(label_pseudo, BorderLayout.NORTH);
	}


	private void initPanel() {
		setLayout(new BorderLayout());
		setBackground(joueur.getCouleur());
		setPreferredSize(new Dimension(150, 50));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
	}
	


	public void updateCurrentPlayer(Joueur joueur) {
		isCurrent = joueur == this.joueur;
		
		label_pseudo.setFont(new Font("Comic sans ms", isCurrent ? Font.BOLD : Font.PLAIN, 14));
	}

}
