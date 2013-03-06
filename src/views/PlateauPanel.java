package views;

import javax.swing.*;
import java.awt.*;

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
		origin = new Point(diametre / 2 - cell_spacing/2, diametre / 2 - cell_spacing/2);
	}
	
	public void paintComponent(Graphics g) {
		int taille = partie.getTaillePlateau();
		
		g.setColor(bg_color);
		g.fillRect(0, 0, dimension.width, dimension.height);
		
		g.setColor(cell_color);
		
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
		Coords c = coords.mul(cell_spacing).toWindow();
		
		g.fillOval(origin.x + c.x, origin.y + c.y, cell_size, cell_size);
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
