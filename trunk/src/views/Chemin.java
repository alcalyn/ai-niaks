package views;

import java.awt.Color;
import java.awt.Graphics;

import model.Coords;

public class Chemin {
	
	private static final int passage_size = 12;
	private static final Color passage_color = Color.RED;
	private static final Color line_color = Color.RED;
	
	
	
	private PlateauPanel plateau_panel;
	private Coords [] chemin;
	private int taille;
	
	
	public Chemin(PlateauPanel plateau_panel, Coords[] chemin) {
		this.plateau_panel = plateau_panel;
		this.chemin = chemin;
		this.taille = chemin.length;
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
		Coords pw = p.mul(PlateauPanel.cell_spacing).toWindow(plateau_panel.getRotation());
		
		g.setColor(passage_color);
		g.fillOval(
				pw.x - passage_size / 2 + plateau_panel.getOrigin().x,
				pw.y - passage_size / 2 + plateau_panel.getOrigin().y,
				passage_size,
				passage_size
		);
	}
	
	private void drawLine(Graphics g, Coords p0, Coords p1) {
		Coords w0 = p0.mul(PlateauPanel.cell_spacing).toWindow(plateau_panel.getRotation());
		Coords w1 = p1.mul(PlateauPanel.cell_spacing).toWindow(plateau_panel.getRotation());
		
		g.setColor(line_color);
		g.drawLine(
				w0.x + plateau_panel.getOrigin().x,
				w0.y + plateau_panel.getOrigin().y,
				w1.x + plateau_panel.getOrigin().x,
				w1.y + plateau_panel.getOrigin().y
		);
				
	}

}
