package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Niaks;
import controllers.PseudoSubmit;

public class CardPseudo extends JPanel {
	
	private static final long serialVersionUID = -3836275752302660508L;
	
	private Niaks niaks;
	
	
	private JLabel label;
	private JTextField pseudo;
	private JButton button;
	
	
	public CardPseudo(Niaks niaks) {
		super();
		this.niaks = niaks;
		setLayout(new FlowLayout());
		
		setBackground(new Color(0xFFFFFF));
		
		label = new JLabel("Votre pseudo :");
		label.setFont(new Font("Comic sans MS", Font.PLAIN, 14));
		pseudo = new JTextField(16);
		pseudo.setFont(new Font("Comic sans MS", Font.PLAIN, 16));
		button = new JButton("Valider");
		button.addActionListener(new PseudoSubmit(niaks, pseudo));
		
		add(label);
		add(pseudo);
		add(button);
	}

}
