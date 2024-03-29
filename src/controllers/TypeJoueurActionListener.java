package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.Humain;
import model.Ordinateur;
import model.PartiePreparator;
import model.Strategies;
import model.Strategy;

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
		JComboBox c = (JComboBox) e.getSource();
		
		int select = c.getSelectedIndex();
		if(index == partie_preparator.getNbJoueur()) {
			switch(select) {
				case 1:
					partie_preparator.addJoueur(new Humain(Humain.getRandomPseudo()));
					break;
					
				case 2:
					Strategy strat = Strategies.getStrategyByDifficult("Moyen");
					partie_preparator.addJoueur(new Ordinateur(strat));
					break;
			}
		} else {
			switch(select) {
				case 0:
					partie_preparator.removeJoueur(index);
					break;
					
				case 1:
					partie_preparator.removeJoueur(index);
					partie_preparator.addJoueur(new Humain(Humain.getRandomPseudo()), index);
					break;
					
				case 2:
					partie_preparator.removeJoueur(index);
					Strategy strat = Strategies.getStrategyByDifficult("Moyen");
					partie_preparator.addJoueur(new Ordinateur(strat), index);
					break;
		}
		}
	}

}
