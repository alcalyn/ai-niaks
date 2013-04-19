package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import exceptions.ProfilNotSetNiaksException;

import model.Niaks;
import model.ProfilManager;

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
		ProfilManager.addPseudo(pseudo.getText());
		niaks.setProfil(pseudo.getText());
		try {
			niaks.startPreparation();
		} catch (ProfilNotSetNiaksException e1) {
			e1.printStackTrace();
		}
	}

}
