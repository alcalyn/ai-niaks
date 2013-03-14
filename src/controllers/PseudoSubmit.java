package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.Niaks;
import model.ProfilNotSetNiaksException;

public class PseudoSubmit implements ActionListener {
	
	private Niaks niaks;
	private JTextField pseudo;
	
	
	public PseudoSubmit(Niaks niaks, JTextField pseudo) {
		super();
		this.niaks = niaks;
		this.pseudo = pseudo;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		niaks.setProfil(pseudo.getText());
		try {
			niaks.startPreparation();
		} catch (ProfilNotSetNiaksException e1) {
			e1.printStackTrace();
		}
	}

}
