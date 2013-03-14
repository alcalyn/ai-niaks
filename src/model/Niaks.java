package model;

import niakwork.Niakwork;

public class Niaks extends Model {
	
	public static final int
		PSEUDO = 2,
		PREPARATION = 3,
		PARTIE = 4;
	
	private String pseudo = null;
	private Niakwork niakwork = null;
	
	
	
	private PartiePreparator partie_preparator = null;
	private Partie partie = null;
	
	
	public Niaks() {
	}
	
	
	
	public void startPreparation() throws ProfilNotSetNiaksException {
		if(pseudo != null) {
			partie_preparator = new PartiePreparator(this, new Humain(pseudo));
			notifyEtat(PREPARATION);
		} else {
			throw new ProfilNotSetNiaksException("Aucun profil n'est défini");
		}
	}
	
	public void startPartie() throws PartieNotReadyToStartNiaksException {
		partie = partie_preparator.createPartie();
		partie_preparator = null;
		notifyEtat(PARTIE);
	}
	
	
	public void setProfil(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
	public void enableNiakwork() {
		if(niakwork == null) {
			niakwork = new Niakwork(this);
		}
		
		niakwork.enable();
	}
	
	public void disableNiakwork() {
		if(niakwork != null) {
			niakwork.disable();
			niakwork = null;
		}
	}
	
	public boolean isNiakworkEnabled() {
		return niakwork != null && niakwork.isEnabled();
	}
	
	
	
	
	public Niakwork getNiakwork() {
		return niakwork;
	}


	public PartiePreparator getPartiePreparator() {
		return partie_preparator;
	}
	
	public Partie getPartie() {
		return partie;
	}

}
