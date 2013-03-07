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
	private PionPanel[] pions;
	
	private Point origin;
	private int diametre;
	private Dimension dimension;
	
	private Coords mouse_coords = new Coords();
	
	private double rotation = 0;
	
	
	public PlateauPanel(Partie partie) {
		super();
		this.partie = partie;
		
		addMouseListeners();
		
		diametre = (int) Math.ceil((cell_spacing * 2 * partie.getTaillePlateau()) * Math.sqrt(3) + (2 * panel_padding));
		dimension = new Dimension(diametre, diametre);
		origin = new Point(diametre/2, diametre/2);
		
		initPions();
	}
	
	private void addMouseListeners() {
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				boolean over = false;
				
				Point m = new Point(
					e.getX() - origin.x,
					e.getY() - origin.y
				);
				
				mouse_coords = new Coords(
					(int) Math.round((double) (m.x + m.y/2) / (double) cell_spacing),
					(int) Math.round((double) (-m.y / (Math.sqrt(3) / 2)) / (double) cell_spacing)
				);
				
				for (PionPanel pion : pions) {
					Point p = new Point(
						pion.getX() + pion_size / 2,
						pion.getY() + pion_size / 2
					);
					
					if(p.distance(m) <= (pion_size / 2)) {
						pion.setOver(true);
						over = true;
					} else {
						pion.setOver(false);
					}
				}
				
				
				setCursor(new Cursor(over ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
				
				repaint();
			}
			
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initPions() {
		int nb_pion = partie.getNbJoueur() * (partie.getTaillePlateau() * (partie.getTaillePlateau() + 1)) / 2 ;
		pions = new PionPanel[nb_pion];
		
		Pion [][] pions_model = partie.getPlateau().getPions();
		
		int k = 0;
		
		for(int i=0;i<pions_model.length;i++) {
			for(int j=0;j<pions_model[i].length;j++) {
				PionPanel pion_panel = new PionPanel(pions_model[i][j]);
				
				add(pion_panel);
				
				this.pions[k++] = pion_panel;
			}
		}
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
		for (PionPanel pion : pions) {
			pion.paint(g);
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
		
		if(coords.equals(mouse_coords)) {
			g.setColor(Color.WHITE);
		}
		
		Coords c = coords.mul(cell_spacing).toWindow(rotation);
		
		
		g.fillOval(
			c.x - cell_size / 2,
			c.y - cell_size / 2,
			cell_size,
			cell_size
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
