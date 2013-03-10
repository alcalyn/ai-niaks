package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import model.Coords;
import model.Coords3;
import model.Joueur;
import model.Observer;
import model.Partie;
import model.Pion;



public class PlateauPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1809255821090205779L;
	
	public static final Color bg_color = Color.WHITE;
	public static final Color plateau_color = new Color(0xFFFFCC);
	public static final Color cell_color = new Color(0x000000);
	public static final int pion_size = 28;
	public static final int cell_size = 16;
	public static final int cell_spacing = 32;
	public static final int panel_padding = 24;
	
	
	
	private Partie partie;
	private PionPanel[] pions;
	
	private Point origin;
	private int diametre;
	private Dimension dimension;
	
	private PionPanel pion_over = null;
	private boolean dragging = false;
	
	private Coords mouse_coords = new Coords();
	
	private double rotation = 0;
	
	
	public PlateauPanel(Partie partie) {
		super();
		this.partie = partie;
		
		addMouseListeners();
		
		diametre = (int) Math.ceil((cell_spacing * 2 * partie.getTaillePlateau()) * Math.sqrt(3) + (2 * panel_padding) + pion_size);
		dimension = new Dimension(diametre, diametre);
		origin = new Point(diametre/2, diametre/2);
		
		initPions();
	}
	
	private void addMouseListeners() {
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				boolean over = false;
				PionPanel pp = pion_over;
				
				Point m = new Point(
					e.getX() - origin.x,
					e.getY() - origin.y
				);
				
				AffineTransform.getRotateInstance(- rotation).transform(m, m);
				
				mouse_coords = new Coords(
					(int) Math.round((double) (m.x + m.y/2) / (double) cell_spacing),
					(int) Math.round((double) (-m.y / (Math.sqrt(3) / 2)) / (double) cell_spacing)
				);
				
				for (PionPanel pion : pions) {
					Point p = new Point(
						pion.getX() + pion_size / 2 - origin.x,
						pion.getY() + pion_size / 2 - origin.y
					);
					
					AffineTransform.getRotateInstance(- rotation).transform(p, p);
					
					if(!dragging) {
						if(p.distance(m) <= (pion_size / 2)) {
							pion_over = pion;
							pion.setOver(true);
							over = true;
						} else {
							pion.setOver(false);
						}
					}
				}
				
				
				
				if(dragging) {
					pion_over.drag(new Coords(e.getX() - origin.x, e.getY() - origin.y));
					cursorHand();
				} else {
					setCursor(new Cursor(over ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
					if(!over) pion_over = null;
				}
				
				if(pion_over != pp || dragging) repaint();
			}
			
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
		
		addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				if(dragging) {
					dragging = false;
					pion_over.stopDrag();
					pionMoved(pion_over.getPionModel(), mouse_coords);
					repaint();
				}
			}

			public void mousePressed(MouseEvent e) {
				if(pion_over != null) {
					dragging = true;
					setForeground(pion_over);
					pion_over.drag(new Coords(e.getX() - origin.x, e.getY() - origin.y));
					repaint();
				}
			}

			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
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
				PionPanel pion_panel = new PionPanel(pions_model[i][j], this);
				
				add(pion_panel);
				
				this.pions[k++] = pion_panel;
			}
		}
	}
	
	
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
		
		if(dragging && coords.equals(mouse_coords)) {
			g.setColor(Color.GRAY);
			g.drawOval(
				c.x - pion_size / 2 + origin.x,
				c.y - pion_size / 2 + origin.y,
				pion_size,
				pion_size
			);
		}
		
		g.fillOval(
			c.x - cell_size / 2 + origin.x,
			c.y - cell_size / 2 + origin.y,
			cell_size,
			cell_size
		);
	}
	
	private void setForeground(PionPanel pion_over) {
		remove(pion_over);
		add(pion_over, 0);
	}
	
	private void pionMoved(Pion pion, Coords coords) {
		System.out.println("Pion moved : "+pion+" to "+coords);
		
		// test :
		partie.getPlateau().movePion(pion, coords);
	}
	
	public void setBottomBranch(int branch) {
		setRotation((((branch % 6) + 2) * Math.PI) / 3);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		repaint();
	}
	
	
	public double getRotation() {
		return rotation;
	}
	
	public Point getOrigin() {
		return origin;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void cursorHand() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void cursorDefault() {
		setCursor(Cursor.getDefaultCursor());
	}
	
	public void updatePions(Pion[][] pions) {
	}

	public void updateCurrentPlayer(Joueur joueur) {
	}
	
}
