package views;

import java.awt.Color;
import java.awt.Graphics;

import model.Coords;
import model.OriginRotation;

public class Chemin {
	
	private int passage_size = 12;
	private int cell_spacing = PlateauPanel.cell_spacing;
	private static final Color passage_color = Color.RED;
	private static final Color line_color = Color.RED;
	
	
	
	private OriginRotation abstractPlateau;
	private Coords [] chemin;
	private int taille;
	
	
	public Chemin(OriginRotation abstractPlateau, Coords[] chemin) {
		this.abstractPlateau = abstractPlateau;
		this.chemin = chemin;
		this.taille = chemin.length;
	}
	
	public void setGraphicsDimension(int passage_size, int cell_spacing) {
		this.passage_size = passage_size;
		this.cell_spacing = cell_spacing;
	}
	
	public void drawChemin(Graphics g) {
		for(int i=1;i<taille;i++) {
			drawLine(g, chemin[i-1], chemin[i]);
		}
		
		for (Coords passage : chemin) {
			drawPassage(g, passage);
		}
	}
	
	
	private void drawPassage(Graphics g, Coords p) {
		Coords pw = p.mul(cell_spacing).toWindow(abstractPlateau.getRotation());
		
		g.setColor(passage_color);
		g.fillOval(
				pw.x - passage_size / 2 + abstractPlateau.getOrigin().x,
				pw.y - passage_size / 2 + abstractPlateau.getOrigin().y,
				passage_size,
				passage_size
		);
		
		g.setColor(new Color(0x22000000, true));
		g.fillOval(
				pw.x - passage_size / 2 + abstractPlateau.getOrigin().x + 2,
				pw.y - passage_size / 2 + abstractPlateau.getOrigin().y + 2,
				passage_size - 4,
				passage_size - 4
		);
	}
	
	private void drawLine(Graphics g, Coords p0, Coords p1) {
		Coords w0 = p0.mul(cell_spacing).toWindow(abstractPlateau.getRotation());
		Coords w1 = p1.mul(cell_spacing).toWindow(abstractPlateau.getRotation());
		
		g.setColor(line_color);
		g.drawLine(
				w0.x + abstractPlateau.getOrigin().x,
				w0.y + abstractPlateau.getOrigin().y,
				w1.x + abstractPlateau.getOrigin().x,
				w1.y + abstractPlateau.getOrigin().y
		);
				
	}

}
