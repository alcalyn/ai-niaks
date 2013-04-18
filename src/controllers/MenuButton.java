package controllers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import niakwork.NiakworkHostSocket;

import views.About;
import views.MinimaxView;
import views.NiaksFrame;

import exceptions.NiaksException;
import exceptions.PartieNotReadyToStartNiaksException;

import model.Niaks;

public class MenuButton extends ModelActionner {
	
	public static final int
		LANCER_PARTIE = 4,
		CONNECT = 5,
		DISCONNECT = 6,
		SEARCH_LOCAL = 7,
		CONNECT_TO = 8,
		JOIN_HOST = 9,
		SELECT_TAILLE_PLATEAU = 10,
		SHOW_MINIMAX_VIEW = 11,
		SHOW_ABOUT_PROJECT = 12,
		NEW_GAME = 13,
		RESTART_GAME = 14;
	
	
	
	
	private int action;
	private NiaksFrame frame;
	private Object [] args = null;
	
	
	public MenuButton(Niaks niaks, ExceptionCatcher view, int action) {
		super(niaks, view);
		this.action = action;
		this.frame = (NiaksFrame) view;
	}
	public MenuButton(Niaks niaks, ExceptionCatcher view, int action, Object [] args) {
		super(niaks, view);
		this.action = action;
		this.frame = (NiaksFrame) view;
		this.args = args;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(action) {
			case SELECT_TAILLE_PLATEAU:
				niaks.getPartiePreparator().setPlateauSize((Integer) args[0]);
				break;
				
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
				
			case SEARCH_LOCAL:
				niaks.getNiakwork().searchHost();
				break;
			
			case CONNECT_TO:
				String ip = JOptionPane.showInputDialog(
					frame,
					"Entrez l'adresse de l'hôte",
					"Se connecter à l'adresse...",
					JOptionPane.PLAIN_MESSAGE
				);
				
				niaks.getNiakwork().connectTo(ip);
				break;
			
			case JOIN_HOST:
				NiakworkHostSocket host = (NiakworkHostSocket) args[0];
				host.queryJoin();
				JOptionPane.showConfirmDialog(
						frame,
						"Join query has been sent. Please wait for host response...",
						"Niakwork",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE
				);
				break;
			
			case SHOW_MINIMAX_VIEW:
				if(niaks.getEtat() == Niaks.PARTIE) {
					new MinimaxView(niaks.getPartie().getMinimax());
				}
				break;
			
			case SHOW_ABOUT_PROJECT:
				new About();
				break;
				
			default:
				System.out.println("Erreur : code action inconnu");
		}
	}

}
