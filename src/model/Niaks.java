package model;

import niakwork.Niakwork;
import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayer;
import niakwork.NiakworkPlayerSocket;
import exceptions.PartieNotReadyToStartNiaksException;
import exceptions.ProfilNotSetNiaksException;

public class Niaks extends Model {
	

	public static final int
		PSEUDO = 2,
		PREPARATION = 3,
		PARTIE = 4;
	
	private String pseudo = null;
	private Niakwork niakwork = null;


	private int etat;
	private PartiePreparator partie_preparator = null;
	private PartiePreparator partie_preparator_last = null;
	private Partie partie = null;
	
	private boolean isHost = true;
	private NiakworkHostSocket host = null;
	
	
	public Niaks() {
		etat = PSEUDO;
	}
	
	
	
	public void startPreparation() throws ProfilNotSetNiaksException {
		if(pseudo != null) {
			if(partie_preparator_last == null) {
				partie_preparator = new PartiePreparator(this, new Humain(pseudo));
			} else {
				partie_preparator = partie_preparator_last;
				partie_preparator_last = null;
			}
			
			etat = PREPARATION;
			notifyEtat(PREPARATION);
		} else {
			throw new ProfilNotSetNiaksException("Aucun profil n'est défini");
		}
	}
	
	public void startPartie() throws PartieNotReadyToStartNiaksException {
		if(isHost) {
			for (Joueur j : partie_preparator.getJoueurs()) {
				if(j instanceof NiakworkPlayer) {
					((NiakworkPlayer) j).getNiakworkPlayerSocket().queryStartGame();
					addObserver((NiakworkPlayer) j);
				}
			}
		}
		
		partie = partie_preparator.createPartie();
		partie_preparator_last = partie_preparator;
		partie_preparator = null;
		etat = PARTIE;
		
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
	
	
	public void niakworkServerFound(NiakworkHostSocket nssocket, String pseudo) {
		System.out.println("Model > server found : "+pseudo);
		notifyNiakworkServerFound(nssocket, pseudo);
	}
	
	public void niakworkClientWantJoin(NiakworkPlayerSocket npsocket, String pseudo) {
		if(etat == PREPARATION) {
			notifyNiakworkClientWantJoin(npsocket, pseudo);
		} else {
			npsocket.queryGameAlreadyStarted();
		}
	}
	
	public void niakworkAcceptClient(NiakworkPlayerSocket npsocket, String pseudo) {
		npsocket.queryAcceptJoin();
		partie_preparator.addJoueur(new NiakworkPlayer(pseudo, npsocket));
	}
	
	public void niakworkDenyClient(NiakworkPlayerSocket npsocket) {
		npsocket.queryDenyJoin();
	}
	
	public void niakworkHostJoined(NiakworkHostSocket nssocket) {
		isHost = false;
		host = nssocket;
		notifyNiakworkHostAccept(nssocket);
	}
	
	public void niakworkHostDenied(NiakworkHostSocket nssocket, String reason) {
		notifyNiakworkHostDenied(nssocket, reason);
	}
	
	public void niakworkUpdatePartiePreparator(NiakworkHostSocket nssocket, String[] joueurs, int taille_plateau) {
		partie_preparator.removeJoueurs();
		
		for (String joueur : joueurs) {
			if(joueur.equals(pseudo)) {
				partie_preparator.addJoueur(new Humain(pseudo));
			} else {
				partie_preparator.addJoueur(new NiakworkPlayer(joueur, null));
			}
		}
		
		
		Joueur [] joueursArray = new Joueur[partie_preparator.getNbJoueur()];
		partie_preparator.getJoueurs().toArray(joueursArray);
		this.notifyJoueurs(joueursArray);
		
		partie_preparator.setPlateauSize(taille_plateau);
		this.notifyTaillePlateau(partie_preparator.getPlateauSize());
	}


	public PartiePreparator getPartiePreparator() {
		return partie_preparator;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public int getEtat() {
		return etat;
	}
	
	public NiakworkHostSocket getHost() {
		return host;
	}



	public void niakworkUpdateCurrentPlayer(String pseudo) {
		for (Joueur j : partie.getJoueurs()) {
			if(j.getPseudo().equals(pseudo)) {
				partie.setJoueur(j);
				break;
			}
		}
		
	}



	public void niakworkUpdatePions(Coords [][] coords) {
		Pion [][] pions = partie.getPlateau().getPions();
		
		for(int i=0;i<pions.length;i++) {
			for(int j=0;j<pions[i].length;j++) {
				if(!pions[i][j].getCoords().equals(coords[i][j])) {
					partie.getPlateau().movePion(pions[i][j], coords[i][j]);
				}
			}
		}
		
		notifyPions(pions);
	}
	
	
	public void niakworkUpdateWinner(Joueur joueur) {
		joueur.setWon(true);
		notifyJoueurWon(joueur);
	}



	public void gameFinished() {
		notifyGameFinished();
		
		try {
			startPreparation();
		} catch (ProfilNotSetNiaksException e) {
			e.printStackTrace();
		}
	}

}
