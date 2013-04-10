package views;

import java.io.PrintStream;

import niakwork.NiakworkHostSocket;
import niakwork.NiakworkPlayerSocket;
import model.Coup;
import model.Joueur;
import model.Model;
import model.Niaks;
import model.Observer;
import model.Partie;
import model.Pion;

public class ConsoleView implements Observer {
	
	
	
	private PrintStream ps;

	public ConsoleView(Model model) {
		model.addObserver(this);
		this.ps = System.out;
	}
	
	public ConsoleView(Model model, PrintStream ps) {
		model.addObserver(this);
		this.ps = ps;
	}
	
	
	@Override
	public void updateProfil(String pseudo) {
		ps.println("profil updated : "+pseudo);
	}

	@Override
	public void updateEtat(int etat_partie) {
		String s = "???";
		
		switch (etat_partie) {
			case Niaks.PREPARATION:
				s = "preparation";
				break;
			
			case Niaks.PSEUDO:
				s = "ecran d'accueil";
				break;
			
			case Niaks.PARTIE:
				s = "en cours";
				break;
		}
		
		ps.println("partie est maintenant : "+s);
	}

	@Override
	public void updateTaillePlateau(int taille) {
		ps.println("Taille plateau definie sur "+taille);
	}

	@Override
	public void updateJoueurs(Joueur[] joueurs) {
		ps.println("joueurs updated : "+joueurs.length);
	}

	@Override
	public void updatePions(Pion[][] pions, Coup coup) {
		ps.println("Positions des pions updated");
	}

	@Override
	public void updateCurrentPlayer(Joueur joueur) {
		ps.println("A "+joueur.getPseudo()+" de jouer");
	}

	@Override
	public void updateJoueurWon(Joueur joueur) {
		ps.println(joueur.getPseudo()+" a gagne");
	}

	@Override
	public void updateNiakwork(boolean isEnabled) {
		ps.println("Niakwork a ete "+(isEnabled ? "active" : "desactive"));
	}

	@Override
	public void updateNiakworkClientWantJoin(NiakworkPlayerSocket npsocket,
			String pseudo) {
		ps.println("Le joueur '"+pseudo+"' souhaite joindre la partie");
	}

	@Override
	public void updateNiakworkServerFound(NiakworkHostSocket nssocket,
			String pseudo) {
		ps.println("La recherche de partie en ligne a trouve la partie de '"+pseudo+"'");
	}

	@Override
	public void updateNiakworkHostDenied(NiakworkHostSocket nssocket,
			String reason) {
		ps.println("La partie que vous avez tente de joindre vous a refuse car : "+reason);
	}

	@Override
	public void updateNiakworkHostAccept(NiakworkHostSocket nssocket) {
		ps.println("La partie que vous avez tente de joindre vous a accepte");
	}

	@Override
	public void updateGameFinished() {
		ps.println("La partie est terminee ! tchao amigo");
	}
	
}
