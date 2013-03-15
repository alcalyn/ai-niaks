package controllers;

import java.awt.event.ActionEvent;

import exceptions.NiaksException;
import exceptions.PartieNotReadyToStartNiaksException;

import model.Niaks;

public class MenuButton extends ModelActionner {
	
	public static final int
		LANCER_PARTIE = 4,
		CONNECT = 5,
		DISCONNECT = 6;
	
	private int action;
	
	
	public MenuButton(Niaks niaks, ExceptionCatcher view, int action) {
		super(niaks, view);
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(action) {
			case LANCER_PARTIE:
				try {
					niaks.startPartie();
				} catch (PartieNotReadyToStartNiaksException e) {
					catchException(e);
				}
				break;
				
			case CONNECT:
				try {
					niaks.getNiakwork().startServer();
				} catch (NiaksException e) {
					catchException(e);
				}
				niaks.getNiakwork().searchHost();
				break;
				
			case DISCONNECT:
				niaks.disableNiakwork();
				break;
				
			default:
				System.out.println("Erreur : code action inconnu");
		}
	}

}
