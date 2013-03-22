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
		label_pseudo.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		label_pseudo.setBackground(Color.WHITE);
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

		setBorder(isCurrent ?
				BorderFactory.createRaisedSoftBevelBorder() :
				BorderFactory.createEmptyBorder(2, 4, 2, 4)
		);
		setBackground(isCurrent ?
				this.joueur.getCouleur() :
				new Color(this.joueur.getCouleur().getRGB() + 0x66000000, true)
		);
		label_pseudo.setBackground(isCurrent ? Color.WHITE : new Color(0xF8F8F8));
		label_pseudo.setFont(new Font("Comic sans ms", isCurrent ? Font.BOLD : Font.PLAIN, 14));
		label_pseudo.setForeground(isCurrent ? Color.BLACK : Color.GRAY);
	}

}
