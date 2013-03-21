package controllers;

import javax.swing.JTextField;

import model.Joueur;

public class TFPseudoListener extends TFListener {
	
	
	private Joueur j;

	public TFPseudoListener(JTextField tf, Joueur j) {
		super(tf);
		this.j = j;
	}

	@Override
	public void changeEvent(String s) {
		j.setPseudo(s);
	}

}
