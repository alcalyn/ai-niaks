package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.Coords;
import model.Coords3;
import model.OriginRotation;
import model.Partie;
import model.Pion;
import model.Plateau;

public class PlateauMiniature extends JPanel implements OriginRotation {
	
	private static final long serialVersionUID = 1920438290936379379L;
	
	public static final Color bg_color = Color.WHITE;
	public static final Color plateau_color = new Color(0xAAAAAA);
	public static final Color cell_color = new Color(0x000000);
	public static final int pion_size = 8;
	public static final int cell_size = 3;
	public static final int cell_spacing = 9;
	public static final int panel_padding = 0;
	public static final Color activity_color = new Color(0xFFAA44);
	
	
	private Plateau plateau;
	private Partie partie;
	
	private Point origin;
	private int diametre;
	private Dimension dimension;
	
	private Chemin chemin = null;
	
	private double rotation = 2*Math.PI/3;
	
	
	public PlateauMiniature(Plateau plateau) {
		super();
		this.plateau = plateau;
		partie = plateau.getPartie();
		
		diametre = (int) Math.ceil((cell_spacing * 2 * partie.getTaillePlateau()) * Math.sqrt(3) + (2 * panel_padding) + pion_size);
		dimension = new Dimension(diametre, diametre);
		origin = new Point(diametre/2, diametre/2);
		
		if(plateau.getLastCoup() != null && plateau.getLastCoup().getChemin() != null) {
			chemin = new Chemin(this, plateau.getLastCoup().getChemin());
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		origin = new Point(getWidth()/2, getHeight()/2);
		
		g.setColor(bg_color);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int taille = partie.getTaillePlateau();
		
		g.setColor(plateau_color);
		g.fillOval(origin.x - dimension.width/2, origin.y - dimension.height/2, dimension.width, dimension.height);
		
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
		
		if(chemin != null) chemin.drawChemin(g);
		
		for (Pion [] pions : plateau.getPions()) {
			for (Pion pion : pions) {
				paintPion(g, pion);
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
		Coords c = coords.mul(cell_spacing).toWindow(rotation);
		
		g.setColor(cell_color);
		
		g.fillOval(
			c.x - cell_size / 2 + origin.x,
			c.y - cell_size / 2 + origin.y,
			cell_size,
			cell_size
		);
		
		/*
		// tests
		g.setColor(Color.RED);
		g.setFont(new Font("Courier new", Font.BOLD, 12));
		g.drawString(
				Integer.toString(coords.distance(new Coords(-4, 8))),
				c.x + origin.x,
				c.y + origin.y
		);
		*/
	}
	
	private void paintPion(Graphics g, Pion pion) {
		Coords c = pion.getCoords().mul(cell_spacing).toWindow(rotation);
		
		g.setColor(pion.getCouleur());
		
		g.fillOval(
				c.x - pion_size / 2 + origin.x,
				c.y - pion_size / 2 + origin.y,
				pion_size,
				pion_size
		);
	}


	@Override
	public double getRotation() {
		return rotation;
	}


	@Override
	public Point getOrigin() {
		return origin;
	}

}
