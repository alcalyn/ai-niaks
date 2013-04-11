package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import model.Coords;
import model.Pion;
import model.Res;

public class PionPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8890517428788892944L;
	
	
	private static final int shadow_size = 2;
	
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
		
		g.setColor(pion.getCouleur());
		
		if(isOver) {
			g.setColor(new Color(g.getColor().getRGB() + 0xCC000000, true));
		}
		
		if(drag == null) {
			setPosition(coords.x, coords.y);
		} else {
			setPosition(drag.x, drag.y);
		}
		
		g.fillOval(shadow_size, shadow_size, PlateauPanel.pion_size, PlateauPanel.pion_size);
		
		int scale = (int) (PlateauPanel.pion_size * 1.43);
		g.drawImage(Res.getImage("pion-ombre.png").getScaledInstance(scale, scale, Image.SCALE_SMOOTH), -3, -5, null);
	}
	
	
	public void setPosition(int x, int y) {
		setBounds(
			x - PlateauPanel.pion_size / 2 + plateau_panel.getOrigin().x - shadow_size,
			y - PlateauPanel.pion_size / 2 + plateau_panel.getOrigin().y - shadow_size,
			PlateauPanel.pion_size + shadow_size * 2,
			PlateauPanel.pion_size + shadow_size * 2
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
	
	public Pion getPionModel() {
		return pion;
	}

}
