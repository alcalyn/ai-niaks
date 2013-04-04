package views;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BreadcrumbNodes extends JPanel {
	
	private static final long serialVersionUID = -8422929513894160858L;
	
	
	private MinimaxNodeView [] bread = null;


	public BreadcrumbNodes() {
		super();
		
		setLayout(new GridLayout(1, 12));
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	
	public void updateBread(MinimaxNodeView [] bread) {
		if(this.bread != null) {
			for (MinimaxNodeView node : this.bread) {
				remove(node);
			}
		}
		
		this.bread = bread;
		
		for (MinimaxNodeView node : bread) {
			add(node);
		}
	}
	

}
