package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Coords;
import model.Pion;

public class PionPanel extends JPanel {
	
	
	private Pion pion;
	private boolean isOver = false;
	
	public PionPanel(Pion pion) {
		this.pion = pion;
	}
	
	
	public void paintComponent(Graphics g) {
		Coords coords = pion.getCoords().mul(PlateauPanel.cell_spacing).toWindow();
		
		setBounds(coords.x - PlateauPanel.pion_size / 2, coords.y - PlateauPanel.pion_size / 2, PlateauPanel.pion_size, PlateauPanel.pion_size);
		
		g.setColor(PlateauPanel.joueurColor(pion.getCouleur()));
		
		if(isOver) g.setColor(Color.WHITE);
		
		g.fillOval(0, 0, PlateauPanel.pion_size, PlateauPanel.pion_size);
	}


	public void setOver(boolean b) {
		isOver = b;
	}

}
