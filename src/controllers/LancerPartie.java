package controllers;

import java.awt.event.ActionEvent;

import model.Niaks;
import exceptions.PartieNotReadyToStartNiaksException;

public class LancerPartie extends ModelActionner {
	
	
	
	

	public LancerPartie(Niaks niaks, ExceptionCatcher view) {
		super(niaks, view);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			niaks.startPartie();
		} catch (PartieNotReadyToStartNiaksException e) {
			catchException(e);
		}
	}

}
