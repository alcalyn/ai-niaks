package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.Ordinateur;
import model.PartiePreparator;

public class DifficultListener implements ActionListener {
	
	PartiePreparator partie_preparator;
	Ordinateur ordinateur;
	
	
	public DifficultListener(PartiePreparator partie_preparator, Ordinateur ordinateur) {
		this.partie_preparator = partie_preparator;
		this.ordinateur = ordinateur;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		JComboBox combo = (JComboBox) arg0.getSource();
		ordinateur.setStrategy((String) combo.getSelectedItem());
	}

}
