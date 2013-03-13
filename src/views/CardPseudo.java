package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CardPseudo extends JPanel {
	
	private static final long serialVersionUID = -3836275752302660508L;
	
	
	private JLabel label;
	private JTextField pseudo;
	private JButton button;
	
	
	public CardPseudo() {
		setBackground(new Color(0x88FFFFFF, true));
		
		label = new JLabel("Votre pseudo :");
		label.setFont(new Font("Comic sans MS", Font.PLAIN, 14));
		pseudo = new JTextField(16);
		pseudo.setFont(new Font("Comic sans MS", Font.PLAIN, 16));
		button = new JButton("Valider");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(pseudo.getText());
			}
		});
		
		add(label);
		add(pseudo);
		add(button);
	}

}
