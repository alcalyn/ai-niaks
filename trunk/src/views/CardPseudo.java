package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Niaks;
import controllers.EnterListener;
import controllers.PseudoSubmit;

public class CardPseudo extends JPanel {
	
	private static final long serialVersionUID = -3836275752302660508L;
	
	private Niaks niaks;
	
	
	private JLabel label;
	private JTextField pseudo;
	private JButton button;
	
	private PseudoSubmit pseudo_submit;
	
	
	
	public CardPseudo(Niaks niaks) {
		super();
		this.niaks = niaks;
		setLayout(new GridBagLayout());
		
		setBackground(new Color(0xFFFFFF));
		
		label = new JLabel("Votre pseudo :");
		label.setFont(new Font("Comic sans MS", Font.PLAIN, 14));
		pseudo = new JTextField(16);
		pseudo.setFont(new Font("Comic sans MS", Font.PLAIN, 16));
		button = new JButton("Valider");
		pseudo_submit = new PseudoSubmit(this.niaks, pseudo);
		button.addActionListener(pseudo_submit);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 4, 2, 4);
		
		c.gridx = 0;
		c.gridy = 0;
		add(label, c);
		
		c.gridx = 1;
		add(pseudo, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		add(button, c);
		
		pseudo.addKeyListener(new EnterListener() {
			
			@Override
			public void enterReleased(KeyEvent e) {
				pseudo_submit.actionPerformed(null);
			}
		});
	}

}
