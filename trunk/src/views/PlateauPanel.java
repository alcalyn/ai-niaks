package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import model.Coords;
import model.Coords3;
import model.Observer;
import model.Partie;
import model.Pion;



public class PlateauPanel extends JPanel implements Observer {
	
	public static final Color bg_color = new Color(0xFFFFCC);
	public static final Color cell_color = new Color(0x000000);
	public static final int pion_size = 20;
	public static final int cell_size = 16;
	public static final int cell_spacing = 24;
	public static final int panel_padding = 24;
	
	
	
	private Partie partie;
	
	private Point origin;
	private int diametre;
	private Dimension dimension;
	
	
	public PlateauPanel(Partie partie) {
		super();
		this.partie = partie;
		
		diametre = (int) Math.ceil((cell_spacing * 2 * partie.getTaillePlateau()) * Math.sqrt(3) + (2 * panel_padding));
		dimension = new Dimension(diametre, diametre);
		origin = new Point(diametre/2, diametre/2);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.transform(AffineTransform.getTranslateInstance(origin.x, origin.y));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		int taille = partie.getTaillePlateau();
		
		g.setColor(bg_color);
		g.fillOval(-dimension.width/2, -dimension.height/2, dimension.width, dimension.height);
		
		// position milieu
		paintCell(g, new Coords(0, 0));
		
		// hexagone central
		paintTriangle(g, taille, new Coords3(1, 0, 0), new Coords3(0, 0, -1));
		paintTriangle(g, taille, new Coords3(0, 0, -1), new Coords3(0, -1, 0));
		paintTriangle(g, taille, new Coords3(0, -1, 0), new Coords3(-1, 0, 0));
		paintTriangle(g, taille, new Coords3(-1, 0, 0), new Coords3(0, 0, 1));
		paintTriangle(g, taille, new Coords3(0, 0, 1), new Coords3(0, 1, 0));
		paintTriangle(g, taille, new Coords3(0, 1, 0), new Coords3(1, 0, 0));
		
		// branches
		paintTriangle(g, taille, new Coords3(0, taille, 0), new Coords3(1, 0, 0), new Coords3(0, 0, -1));
		paintTriangle(g, taille, new Coords3(taille, 0, 0), new Coords3(0, 0, -1), new Coords3(0, -1, 0));
		paintTriangle(g, taille, new Coords3(0, 0, -taille), new Coords3(0, -1, 0), new Coords3(-1, 0, 0));
		paintTriangle(g, taille, new Coords3(0, -taille, 0), new Coords3(-1, 0, 0), new Coords3(0, 0, 1));
		paintTriangle(g, taille, new Coords3(-taille, 0, 0), new Coords3(0, 0, 1), new Coords3(0, 1, 0));
		paintTriangle(g, taille, new Coords3(0, 0, taille), new Coords3(0, 1, 0), new Coords3(1, 0, 0));
		
		// pions
		Pion [][] pions = partie.getPlateau().getPions();
		
		for(int i=0;i<pions.length;i++) {
			for(int j=0;j<pions[i].length;j++) {
				paintPion(g, pions[i][j]);
			}
		}
	}
	
	
	private void paintTriangle(Graphics g, int taille, Coords3 direction_base, Coords3 direction_etage) {
		paintTriangle(g, taille, new Coords3(0, 0, 0), direction_base, direction_etage);
	}
	
	private void paintTriangle(Graphics g, int taille, Coords3 origine, Coords3 direction_base, Coords3 direction_etage) {
		for(int i = 0;i<taille;i++) {
			for(int j = i;j<taille;j++) {
				paintCell(g, direction_base.mul(j - i + 1).add(direction_etage.mul(i)).add(origine).toCoords());
			}
		}
	}
	
	private void paintCell(Graphics g, Coords coords) {
		g.setColor(cell_color);
		
		Coords c = coords.mul(cell_spacing).toWindow();
		
		
		g.fillOval(
			c.x - cell_size / 2,
			c.y - cell_size / 2,
			cell_size,
			cell_size
		);
	}
	
	private void paintPion(Graphics g, Pion pion) {
		g.setColor(joueurColor(pion.getCouleur()));
		
		Coords c = pion.getCoords().mul(cell_spacing).toWindow();
		
		g.fillOval(
			c.x - pion_size / 2,
			c.y - pion_size / 2,
			pion_size,
			pion_size
		);
	}
	
	public static Color joueurColor(char joueur) {
		switch(joueur) {
			case Pion.BLANC:	return new Color(0xFFFFFF);
			case Pion.NOIR:		return new Color(0x000000);
			case Pion.BLEU:		return new Color(0x0000FF);
			case Pion.JAUNE:	return new Color(0xFFFF00);
			case Pion.VERT:		return new Color(0x00FF00);
			case Pion.ROUGE:	return new Color(0xFF0000);
			
			default: return null;
		}
	}
	
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void updatePions(Pion[][] pions) {
		repaint();
	}

	public void updateCurrentPlayer(char joueur) {
		repaint();
	}
	
}
