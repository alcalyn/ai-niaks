package model;

import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;
import niakwork.Niakwork;
import niakwork.NiakworkPlayerSocket;

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
		notifyProfil(pseudo);
	}
	
	public String getProfil() {
		return pseudo;
	}
	
	
	public void enableNiakwork() {
		if(niakwork == null) {
			niakwork = new Niakwork(this);
			notifyNiakwork(true);
		}
	}
	
	public void disableNiakwork() {
		if(niakwork != null) {
			niakwork.close();
			niakwork = null;
			notifyNiakwork(false);
		}
	}
	
	public boolean isNiakworkEnabled() {
		return niakwork != null;
	}
	
	
	
	
	public Niakwork getNiakwork() {
		if(niakwork == null) {
			enableNiakwork();
		}
		
		return niakwork;
	}
	
	public void niakworkClientFound(NiakworkPlayerSocket npsocket) {
		notifyNiakworkClientFound(npsocket);
	}


	public PartiePreparator getPartiePreparator() {
		return partie_preparator;
	}
	
	public Partie getPartie() {
		return partie;
	}

}
