package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.Humain;
import model.Niaks;
import model.Ordinateur;
import model.PartiePreparator;

public class TypeJoueurActionListener implements ActionListener {
	
	
	private PartiePreparator partie_preparator;
	private int index;
	
	
	public TypeJoueurActionListener(PartiePreparator partie_preparator, int index) {
		super();
		this.partie_preparator = partie_preparator;
		this.index = index;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> c = (JComboBox<String>) e.getSource();
		
		int select = c.getSelectedIndex();
		if(index == partie_preparator.getNbJoueur()) {
			switch(select) {
				case 1:
					partie_preparator.addJoueur(new Humain(""));
					break;
					
				case 3:
					partie_preparator.addJoueur(new Ordinateur(0));
					break;
			}
		} else {
			switch(select) {
				case 0:
					partie_preparator.removeJoueur(index);
					break;
					
				case 1:
					partie_preparator.removeJoueur(index);
					partie_preparator.addJoueur(new Humain(""), index);
					break;
					
				case 3:
					partie_preparator.removeJoueur(index);
					partie_preparator.addJoueur(new Ordinateur(0), index);
					break;
		}
		}
	}

}
