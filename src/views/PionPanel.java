package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Coords;
import model.Pion;

public class PionPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8890517428788892944L;
	private PlateauPanel plateau_panel;
	private Pion pion;
	private boolean isOver = false;
	private Coords drag = null;
	
	public PionPanel(Pion pion, PlateauPanel plateau_panel) {
		this.pion = pion;
		this.plateau_panel = plateau_panel;
	}
	
	
	public void paintComponent(Graphics g) {
		Coords coords = pion.getCoords().mul(PlateauPanel.cell_spacing).toWindow(plateau_panel.getRotation());
		g.setColor(PlateauPanel.joueurColor(pion.getCouleur()));
		if(isOver) g.setColor(new Color(g.getColor().getRGB() + 0x88000000, true));
		
		if(drag == null) {
			setPosition(coords.x, coords.y);
		} else {
			setPosition(drag.x, drag.y);
		}
		
		g.fillOval(0, 0, PlateauPanel.pion_size, PlateauPanel.pion_size);
	}
	
	
	public void setPosition(int x, int y) {
		setBounds(
			x - PlateauPanel.pion_size / 2 + plateau_panel.getOrigin().x,
			y - PlateauPanel.pion_size / 2 + plateau_panel.getOrigin().y,
			PlateauPanel.pion_size,
			PlateauPanel.pion_size
		);
	}

	public void setOver(boolean b) {
		isOver = b;
	}


	public void drag(Coords c) {
		drag = c;
	}
	
	public void stopDrag() {
		drag = null;
	}

}
