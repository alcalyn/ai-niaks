package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.Observer;
import model.Partie;
import model.Pion;



public class NiaksFrame extends JFrame implements Observer {
	
	private static final long serialVersionUID = 7409878114591059470L;
	
	
	
	private Partie partie;
	
	private PlateauPanel plateau_panel;
	
	
	public NiaksFrame(Partie partie) {
		super();
		this.partie = partie;
		initFrame();
		initPlateauPanel();
		
		partie.addObserver(plateau_panel);
	}
	
	
	
	private void initFrame() {
		setTitle("Niaks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout());
		setVisible(true);
	}
	
	private void initPlateauPanel() {
		plateau_panel = new PlateauPanel(partie);
		add(plateau_panel, BorderLayout.CENTER);
	}


	public void updatePions(Pion[][] pions) {
	}


	public void updateCurrentPlayer(char joueur) {
	}
	
	
}
